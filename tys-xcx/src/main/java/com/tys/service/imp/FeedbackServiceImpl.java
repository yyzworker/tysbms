package com.tys.service.imp;

import com.tys.entity.vo.Feedback;
import com.tys.service.FeedbackService;
import com.tys.service.imp.dao.FeedbackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author haoxu
 * @Date 2019/6/14 14:05
 **/
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Transactional
    public int insertFeedback(Feedback feedback){
        return feedbackMapper.insertSelective(feedback);
    }
}
