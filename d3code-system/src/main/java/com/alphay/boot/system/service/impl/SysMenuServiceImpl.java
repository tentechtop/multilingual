package com.alphay.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alphay.boot.common.constant.Constants;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.core.domain.TreeSelect;
import com.alphay.boot.common.core.domain.entity.SysMenu;
import com.alphay.boot.common.core.domain.entity.SysRole;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.domain.vo.MetaVo;
import com.alphay.boot.system.domain.vo.RouterVo;
import com.alphay.boot.system.mapper.SysMenuMapper;
import com.alphay.boot.system.mapper.SysRoleMapper;
import com.alphay.boot.system.mapper.SysRoleMenuMapper;
import com.alphay.boot.system.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 *
 * @author d3code
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements ISysMenuService {
  public static final String PREMISSION_STRING = "perms[\"{0}\"]";

  @Autowired private SysMenuMapper menuMapper;

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysRoleMenuMapper roleMenuMapper;

  /**
   * 根据用户查询系统菜单列表
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  @Override
  public List<SysMenu> selectMenuList(Long userId) {
    return selectMenuList(new SysMenu(), userId);
  }

  /**
   * 查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  @Override
  public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
    List<SysMenu> menuList = null;
    // 管理员显示所有菜单信息
    if (SysUser.isAdmin(userId)) {
      menuList = menuMapper.selectMenuList(menu);
    } else {
      menu.getParams().put("userId", userId);
      menuList = menuMapper.selectMenuListByUserId(menu);
    }
    return menuList;
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  @Override
  public Set<String> selectMenuPermsByUserId(Long userId) {
    List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
    Set<String> permsSet = new HashSet<>();
    for (String perm : perms) {
      if (StringUtils.isNotEmpty(perm)) {
        permsSet.addAll(Arrays.asList(perm.trim().split(",")));
      }
    }
    return permsSet;
  }

  /**
   * 根据角色ID查询权限
   *
   * @param roleId 角色ID
   * @return 权限列表
   */
  @Override
  public Set<String> selectMenuPermsByRoleId(Long roleId) {
    List<String> perms = menuMapper.selectMenuPermsByRoleId(roleId);
    Set<String> permsSet = new HashSet<>();
    for (String perm : perms) {
      if (StringUtils.isNotEmpty(perm)) {
        permsSet.addAll(Arrays.asList(perm.trim().split(",")));
      }
    }
    return permsSet;
  }

  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户名称
   * @return 菜单列表
   */
  @Override
  public List<SysMenu> selectMenuTreeByUserId(Long userId) {
    List<SysMenu> menus = null;
    if (SecurityUtils.isAdmin(userId)) {
      menus =
          list(
              new LambdaQueryWrapperX<SysMenu>()
                  .eq(SysMenu::getStatus, 0)
                  .in(SysMenu::getMenuType, "M", "C")
                  .orderByAsc(SysMenu::getParentId, SysMenu::getOrderNum));
    } else {
      menus = menuMapper.selectMenuTreeByUserId(userId);
    }
    return getChildPerms(menus, 0);
  }

  /**
   * 根据角色ID查询菜单树信息
   *
   * @param roleId 角色ID
   * @return 选中菜单列表
   */
  @Override
  public List<Long> selectMenuListByRoleId(Long roleId) {
    SysRole role = roleMapper.selectRoleById(roleId);
    return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
  }

  /**
   * 构建前端路由所需要的菜单
   *
   * @param menus 菜单列表
   * @return 路由列表
   */
  @Override
  public List<RouterVo> buildMenus(List<SysMenu> menus) {
    List<RouterVo> routers = new LinkedList<RouterVo>();
    for (SysMenu menu : menus) {
      RouterVo router = new RouterVo();
      router.setHidden("1".equals(menu.getVisible()));
      router.setName(getRouteName(menu));
      router.setPath(getRouterPath(menu));
      router.setComponent(getComponent(menu));
      router.setQuery(menu.getQuery());
      router.setMeta(
          new MetaVo(
              menu.getMenuName(),
              menu.getIcon(),
              StringUtils.equals("1", menu.getIsCache()),
              menu.getPath()));
      List<SysMenu> cMenus = menu.getChildren();


      if (CollUtil.isNotEmpty(cMenus) && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
        router.setAlwaysShow(true);
        router.setRedirect("noRedirect");
        router.setChildren(buildMenus(cMenus));
      } else if (isMenuFrame(menu)) {
        router.setMeta(null);
        List<RouterVo> childrenList = new ArrayList<RouterVo>();
        RouterVo children = new RouterVo();
        children.setPath(menu.getPath());
        children.setComponent(menu.getComponent());
        children.setName(StringUtils.capitalize(menu.getPath()));
        children.setMeta(
            new MetaVo(
                menu.getMenuName(),
                menu.getIcon(),
                StringUtils.equals("1", menu.getIsCache()),
                menu.getPath()));
        children.setQuery(menu.getQuery());
        childrenList.add(children);
        router.setChildren(childrenList);
      } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
        router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
        router.setPath("/");
        List<RouterVo> childrenList = new ArrayList<RouterVo>();
        RouterVo children = new RouterVo();
        String routerPath = innerLinkReplaceEach(menu.getPath());
        children.setPath(routerPath);
        children.setComponent(UserConstants.INNER_LINK);
        children.setName(StringUtils.capitalize(routerPath));
        children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
        childrenList.add(children);
        router.setChildren(childrenList);
      }
      routers.add(router);
    }
    return routers;
  }

  /**
   * 构建前端所需要树结构
   *
   * @param menus 菜单列表
   * @return 树结构列表
   */
  @Override
  public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
    List<SysMenu> returnList = new ArrayList<SysMenu>();
    List<Long> tempList = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
    for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
      SysMenu menu = (SysMenu) iterator.next();
      // 如果是顶级节点, 遍历该父节点的所有子节点
      if (!tempList.contains(menu.getParentId())) {
        recursionFn(menus, menu);
        returnList.add(menu);
      }
    }
    if (returnList.isEmpty()) {
      returnList = menus;
    }
    return returnList;
  }

  /**
   * 构建前端所需要下拉树结构
   *
   * @param menus 菜单列表
   * @return 下拉树结构列表
   */
  @Override
  public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
    List<SysMenu> menuTrees = buildMenuTree(menus);
    return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
  }

  /**
   * 是否存在菜单子节点
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  @Override
  public boolean hasChildByMenuId(Long menuId) {
    int result = menuMapper.hasChildByMenuId(menuId);
    return result > 0;
  }

  /**
   * 查询菜单使用数量
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  @Override
  public boolean checkMenuExistRole(Long menuId) {
    int result = roleMenuMapper.checkMenuExistRole(menuId);
    return result > 0;
  }

  /**
   * 校验菜单名称是否唯一
   *
   * @param menu 菜单信息
   * @return 结果
   */
  @Override
  public String checkMenuNameUnique(SysMenu menu) {
    Long menuId = StringUtils.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
    SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
    if (StringUtils.isNotNull(info) && info.getMenuId().longValue() != menuId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }


  /**
   * 获取路由名称
   *
   * @param menu 菜单信息
   * @return 路由名称
   */
  public String getRouteName(SysMenu menu) {
    String routerName = null;
    if (StringUtils.isNotBlank(menu.getComponentName())) {
      routerName = StringUtils.capitalize(menu.getComponentName());
    } else {
      routerName = StringUtils.capitalize(menu.getPath());
    }
    // 非外链并且是一级目录（类型为目录）
    if (isMenuFrame(menu)) {
      routerName = StringUtils.EMPTY;
    }
    return routerName;
  }

  /**
   * 获取路由地址
   *
   * @param menu 菜单信息
   * @return 路由地址
   */
  public String getRouterPath(SysMenu menu) {
    String routerPath = menu.getPath();
    // 内链打开外网方式
    if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
      routerPath = innerLinkReplaceEach(routerPath);
    }
    // 非外链并且是一级目录（类型为目录）
    if (0 == menu.getParentId().intValue()
        && UserConstants.TYPE_DIR.equals(menu.getMenuType())
        && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
      routerPath = "/" + menu.getPath();
    }
    // 非外链并且是一级目录（类型为菜单）
    else if (isMenuFrame(menu)) {
      routerPath = "/";
    }
    return routerPath;
  }

  /**
   * 获取组件信息
   *
   * @param menu 菜单信息
   * @return 组件信息
   */
  public String getComponent(SysMenu menu) {
    String component = UserConstants.LAYOUT;
    if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
      component = menu.getComponent();
    } else if (StringUtils.isEmpty(menu.getComponent())
        && menu.getParentId().intValue() != 0
        && isInnerLink(menu)) {
      component = UserConstants.INNER_LINK;
    } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
      component = UserConstants.PARENT_VIEW;
    }
    return component;
  }

  /**
   * 是否为菜单内部跳转
   *
   * @param menu 菜单信息
   * @return 结果
   */
  public boolean isMenuFrame(SysMenu menu) {
    return menu.getParentId().intValue() == 0
        && UserConstants.TYPE_MENU.equals(menu.getMenuType())
        && menu.getIsFrame().equals(UserConstants.NO_FRAME);
  }

  /**
   * 是否为内链组件
   *
   * @param menu 菜单信息
   * @return 结果
   */
  public boolean isInnerLink(SysMenu menu) {
    return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
  }

  /**
   * 是否为parent_view组件
   *
   * @param menu 菜单信息
   * @return 结果
   */
  public boolean isParentView(SysMenu menu) {
    return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
  }

  /**
   * 根据父节点的ID获取所有子节点
   *
   * @param list 分类表
   * @param parentId 传入的父节点ID
   * @return String
   */
  public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
    List<SysMenu> returnList = new ArrayList<SysMenu>();
    for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
      SysMenu t = (SysMenu) iterator.next();
      // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
      if (t.getParentId() == parentId) {
        recursionFn(list, t);
        returnList.add(t);
      }
    }
    return returnList;
  }

  /**
   * 递归列表
   *
   * @param list 分类表
   * @param t 子节点
   */
  private void recursionFn(List<SysMenu> list, SysMenu t) {
    // 得到子节点列表
    List<SysMenu> childList = getChildList(list, t);
    t.setChildren(childList);
    for (SysMenu tChild : childList) {
      if (hasChild(list, tChild)) {
        recursionFn(list, tChild);
      }
    }
  }

  /** 得到子节点列表 */
  private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
    List<SysMenu> tlist = new ArrayList<SysMenu>();
    Iterator<SysMenu> it = list.iterator();
    while (it.hasNext()) {
      SysMenu n = (SysMenu) it.next();
      if (n.getParentId().longValue() == t.getMenuId().longValue()) {
        tlist.add(n);
      }
    }
    return tlist;
  }

  /** 判断是否有子节点 */
  private boolean hasChild(List<SysMenu> list, SysMenu t) {
    return getChildList(list, t).size() > 0;
  }

  /**
   * 内链域名特殊字符替换
   *
   * @return 替换后的内链域名
   */
  public String innerLinkReplaceEach(String path) {
    return StringUtils.replaceEach(
        path,
        new String[] {Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
        new String[] {"", "", "", "/"});
  }


  /**
   * 查询当前用户的菜单
   * @return
   */
  @Override
  public List<RouterVo> listUserMenus(List<SysMenu> menus) {


    List<SysMenu> catalogList = listCatalog(menus);

    // 获取目录下的子菜单
    Map<Long, List<SysMenu>> childrenMap = getMenuMap(menus);


    List<RouterVo> routerVos = convertUserMenuList(catalogList,childrenMap);

    return null;
  }

  private List<SysMenu> listCatalog(List<SysMenu> menus) {
    System.out.println("顶级菜单List"+menus);

    List<SysMenu> collect = menus.stream()
            .filter(item -> Objects.nonNull(item.getParentId()))
            .sorted(Comparator.comparing(SysMenu::getOrderNum))
            .collect(Collectors.toList());
    System.out.println("处理后的顶级菜单"+collect);
    return collect;
  }

  private Map<Long, List<SysMenu>> getMenuMap(List<SysMenu> menus) {
    return menus.stream()
            .filter(item -> Objects.nonNull(item.getParentId()))
            .collect(Collectors.groupingBy(SysMenu::getParentId));
  }

  private List<RouterVo> convertUserMenuList(List<SysMenu> catalogList, Map<Long, List<SysMenu>> childrenMap) {


    System.out.println("catalogList"+catalogList);
    System.out.println("childrenMap"+childrenMap);


    return null;
  }


}
