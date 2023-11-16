package com.alphay.boot.common.core.convert;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.core.domain.vo.SimpleUserVo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-20T09:10:02+0800",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class SysUserConvertImpl implements SysUserConvert {

    @Override
    public List<SimpleUserVo> convertList(List<SysUser> list) {
        if ( list == null ) {
            return null;
        }

        List<SimpleUserVo> list1 = new ArrayList<SimpleUserVo>( list.size() );
        for ( SysUser sysUser : list ) {
            list1.add( sysUserToSimpleUserVo( sysUser ) );
        }

        return list1;
    }

    protected SimpleUserVo sysUserToSimpleUserVo(SysUser sysUser) {
        if ( sysUser == null ) {
            return null;
        }

        SimpleUserVo simpleUserVo = new SimpleUserVo();

        simpleUserVo.setUserId( sysUser.getUserId() );
        simpleUserVo.setNickName( sysUser.getNickName() );

        return simpleUserVo;
    }
}
