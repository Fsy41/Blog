package com.fusiyi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;

    /**
     * 标签名
     */
    private String name;

    private String remark;
}
