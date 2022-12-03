package com.fusiyi.service;

import com.fusiyi.domain.ResponseResult;
import com.fusiyi.domain.dto.LinkDto;
import com.fusiyi.domain.dto.LinkStatusDto;
import com.fusiyi.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Fusiyi
* @description 针对表【fu_link(友链)】的数据库操作Service
* @createDate 2022-11-22 19:03:10
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto);
}
