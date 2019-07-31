package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.Feedback;
import com.tys.service.FeedbackService;
import com.tys.service.imp.dao.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 王志浩
 * @Date 2019/6/14 14:48
 **/
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Feedback> queryFeedback(Feedback record) {
        PageInfo<Feedback> pageInfo = null;
        if(record.getCurrentPage() != null && record.getPageSize() != null){
            pageInfo = PageHelper.startPage(record.getCurrentPage(), record.getPageSize()).doSelectPageInfo(() -> feedbackMapper.queryFeedback(record));
        }else {
            pageInfo = new PageInfo(feedbackMapper.queryFeedback(record));
        }
        return pageInfo;
    }

    @Override
    public int processFeedback(Integer id) {
        return feedbackMapper.processFeedback(id);
    }
}
