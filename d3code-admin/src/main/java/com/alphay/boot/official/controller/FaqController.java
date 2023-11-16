package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.FaqDTO;
import com.alphay.boot.official.service.FaqService;
import com.alphay.boot.official.vo.FaqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问答模块")
@RestController
@CrossOrigin
@RequestMapping("/official/faq")
public class FaqController  extends BaseController {

    @Autowired
    private FaqService faqService;

    // 根据语言 分类 标签 筛选
    @PreAuthorize("@ss.hasPermi('official:faq:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody FaqVO faqVO) {
        System.out.println("查询List的内容"+faqVO);
        startPage();
        List<FaqDTO> FaqDTO = faqService.selectFaqList(faqVO);
        return getDataTable(FaqDTO);
    }

    //根据faqId 和 lang 查找一个faq
    /**
     * 根据新闻Id获取Seo详细内容
     */
    @PreAuthorize("@ss.hasPermi('official:faq:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(FaqVO faqVO) {
        System.out.println("当前查询的内容"+faqVO);
        FaqDTO faqDTO =faqService.selectFaqById(faqVO);
        System.out.println("查询到的数据是"+faqDTO);
        return success(faqDTO);
    }


    /**
     * 添加一个分类 lang  pageId
     */
    @PreAuthorize("@ss.hasPermi('official:category:add')")
    @Log(title = "添加一个问答", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveFaq(@Validated @RequestBody FaqVO faqVO) {
        faqService.saveFaq(faqVO);
        return AjaxResult.success();
    }

    /**
     * 修改问答
     */
    @ApiOperation(value = "修改问答")
    @PreAuthorize("@ss.hasPermi('official:faq:update')")
    @Log(title = "修改问答", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCategory(@RequestBody FaqVO faqVO){
        System.out.println("修改的数据是"+faqVO);
        faqService.updateFaq(faqVO);
        return success();
    }

    /**
     * 删除问答
     */
    @PreAuthorize("@ss.hasPermi('official:faq:remove')")
    @Log(title = "删除问答", businessType = BusinessType.DELETE)
    @DeleteMapping("/{faqIds}")
    public AjaxResult remove(@PathVariable Integer[] faqIds) {
        faqService.deleteFaqByIds(faqIds);
        return null;
    }

}
