// FeedbackServiceImpl.java
package com.itheima.service.impl;

import com.itheima.mapper.FeedbackMapper;
import com.itheima.pojo.Feedback;
import com.itheima.service.FeedbackService;
import com.itheima.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public void addFeedback(Feedback feedback) {
        feedback.setUserId(CurrentHolder.getCurrentId()); // 当前用户
        feedbackMapper.insert(feedback);
    }
}
