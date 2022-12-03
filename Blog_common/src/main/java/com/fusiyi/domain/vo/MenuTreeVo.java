package com.fusiyi.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MenuTreeVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<MenuTreeVo> children;
}
