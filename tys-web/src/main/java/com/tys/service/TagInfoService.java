package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.TagInfo;

public interface TagInfoService {

    int insert(TagInfo record);

    int update(TagInfo record);

    int deleteById(Integer id);

    TagInfo selectById(Integer id);

    PageInfo<TagInfo> query(TagInfo record);
}
