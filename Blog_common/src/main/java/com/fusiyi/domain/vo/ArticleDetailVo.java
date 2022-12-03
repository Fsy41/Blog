package com.fusiyi.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private Long viewCount;

    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    private Date createTime;


}
