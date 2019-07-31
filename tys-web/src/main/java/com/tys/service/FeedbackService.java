package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.Feedback;

public interface FeedbackService {
    PageInfo<Feedback> queryFeedback(Feedback feedback);
    int processFeedback(Integer id);
}
