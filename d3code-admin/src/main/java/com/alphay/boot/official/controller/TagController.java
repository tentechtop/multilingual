package com.alphay.boot.official.controller;


import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.TagDTO;
import com.alphay.boot.official.service.TagService;
import com.alphay.boot.official.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "标签模块")
@RestController
@CrossOrigin
@RequestMapping("/official/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /** 根据语言获取和类型获取标签列表 */
    @PreAuthorize("@ss.hasPermi('official:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(TagVO tagVO) {
        startPage();
        List<TagDTO> tagDTOList  = tagService.selectNewsList(tagVO);
        return getDataTable(tagDTOList);
    }


    /**
     * 根据id获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:tag:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(TagVO tagVO) {
        System.out.println("查询的数据tagVO"+tagVO);
        TagDTO tagDTO =  tagService.selectTagById(tagVO);
        return success(tagDTO);
    }

    /**
     * 新增标签
     */
    @PreAuthorize("@ss.hasPermi('official:tag:add')")
    @Log(title = "新增标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TagVO tagVO) {
        tagService.saveTag(tagVO);
        return success();
    }


    /**
     * 修改模块
     */
    @PreAuthorize("@ss.hasPermi('official:tag:update')")
    @ApiOperation(value = "修改标签")
    @Log(title = "修改标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult saveOrUpdateModules(@RequestBody TagVO tagVO){
        tagService.updateTag(tagVO);
        return success();
    }

    /**
     * 删除模块
     */
    @PreAuthorize("@ss.hasPermi('official:tag:remove')")
    @Log(title = "删除标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Integer[] tagIds) {

        String s = tagService.deleteTagByIds(tagIds);

        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }


}
