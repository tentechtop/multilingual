package com.alphay.boot.official.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllPageContentDTO {

    //模块列表
    private List<PageDTO> PageDtoList;
}
