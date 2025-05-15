package com.itheima;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void testLog(){
        log.debug("开始计算:");
        int sum = 0;
        int[] nums = {1,2,3,4,5};
        for(int num : nums){
            sum += num;
        }
        log.info("计算结果为:{}",sum);
        log.debug("计算结束");
    }
}
