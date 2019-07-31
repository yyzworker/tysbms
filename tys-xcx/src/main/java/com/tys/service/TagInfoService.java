package com.tys.service;


import com.tys.entity.vo.TagInfo;

import java.util.List;

public interface TagInfoService {

    List<String> getTagInfos(Integer type);

    List<TagInfo> getTagsInfos(List<Integer> types);
}
