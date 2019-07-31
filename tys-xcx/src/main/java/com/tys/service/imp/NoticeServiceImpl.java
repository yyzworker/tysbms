package com.tys.service.imp;

import com.tys.entity.vo.Notice;
import com.tys.service.NoticeService;
import com.tys.service.imp.dao.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice> getNoticeList() {
        return noticeMapper.getNoticeList();
    }
}
