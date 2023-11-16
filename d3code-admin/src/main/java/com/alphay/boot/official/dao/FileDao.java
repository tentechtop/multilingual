package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.EsFileDTO;
import com.alphay.boot.official.dto.FileDTO;
import com.alphay.boot.official.dto.ProductFileDTO;
import com.alphay.boot.official.entity.File;
import com.alphay.boot.official.vo.FileVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao extends BaseMapper<File> {


    @Select("SELECT MAX(file_id) from off_file")
    Integer getMaxId();

    List<FileDTO> selectFileList(FileVO fileVO);

    List<EsFileDTO> selectFileListById(@Param("fileId") Integer fileId);

    FileDTO selectFileById(FileVO fileVO);

    List<ProductFileDTO> getProductFileList(ProductFileDTO productFileDTO);

    ProductFileDTO getProductFileDetail(ProductFileDTO productFileDTO);
}
