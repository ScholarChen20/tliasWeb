// FeedbackController.java
package com.itheima.controller;

import com.itheima.pojo.Feedback;
import com.itheima.pojo.Result;
import com.itheima.service.FeedbackService;
import com.itheima.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Result addFeedback(@RequestBody Feedback feedback) {
        try {
            // 设置当前用户ID
            feedback.setUserId(CurrentHolder.getCurrentId());
            feedbackService.addFeedback(feedback);
            return Result.success("反馈提交成功");
        } catch (Exception e) {
            return Result.error("反馈提交失败: " + e.getMessage());
        }
    }
}
