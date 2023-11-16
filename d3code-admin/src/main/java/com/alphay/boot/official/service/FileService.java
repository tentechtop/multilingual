package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.FileDTO;
import com.alphay.boot.official.dto.ProductFileDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.File;
import com.alphay.boot.official.vo.FileVO;

import java.util.List;

public interface FileService extends IService<File> {
    void saveOrUpdateFile(FileVO fileVO);

    List<FileDTO> selectFileList(FileVO fileVO);

    FileDTO selectFileById(FileVO fileVO);

    void updateFile(FileVO fileVO);

    String deleteFileByIds(Integer[] fileIds);

    List<ProductFileDTO> getProductFileList(ProductFileDTO productFileDTO);

    ProductFileDTO getProductFileDetail(ProductFileDTO productFileDTO);

    void saveProductFileDetail(ProductFileDTO productFileDTO);

    void updateProductFileDetail(ProductFileDTO productFileDTO);

    String removeProductFileDetail(Integer[] fileIds);
}
