package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.FileDTO;
import com.alphay.boot.official.service.FileService;
import com.alphay.boot.official.vo.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "文件模块")
@RestController
@CrossOrigin
@RequestMapping("/official/file")
public class FileController  extends BaseController {


    @Autowired
    private FileService fileService;


    //查看文件列表 根据语言 分类
    @PreAuthorize("@ss.hasPermi('official:category:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody  FileVO fileVO) {
        startPage();
        List<FileDTO> categoryDTOList = fileService.selectFileList(fileVO);
        return getDataTable(categoryDTOList);
    }




    /**
     * 根据id获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:tag:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(FileVO fileVO) {
        FileDTO fileDTO =  fileService.selectFileById(fileVO);
        return success(fileDTO);
    }


    /**
     * 新增文件
     */
    @PreAuthorize("@ss.hasPermi('official:file:insert')")
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveFile(@RequestBody FileVO fileVO){
        fileService.saveOrUpdateFile(fileVO);
        return AjaxResult.success();
    }


    /**
     * 修改文件
     */
    @PreAuthorize("@ss.hasPermi('official:file:update')")
    @ApiOperation(value = "修改文件")
    @Log(title = "修改文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateFile(@RequestBody FileVO fileVO){
        fileService.updateFile(fileVO);
        return success();
    }

    /**
     * 删除文件
     */
    @PreAuthorize("@ss.hasPermi('official:file:remove')")
    @Log(title = "删除文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Integer[] fileIds) {
        String s = fileService.deleteFileByIds(fileIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }


}
