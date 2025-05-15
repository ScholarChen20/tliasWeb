package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(1)    // 设置切面类的优先级，值越小，优先级越高
@Component
@Aspect //当前类为切面类
@Slf4j
public class TimeAspect {
    /**
     * @Before注解表示在目标方法之前执行，
     * @After注解表示在目标方法之后执行，
     * @Around注解表示在目标方法之前和之后都执行。
     * @AfterReturning注解表示在目标方法返回值后执行，
     * @AfterThrowing注解表示在目标方法抛出异常后执行。
     */

    /**
     * 其中*:单个星号通配任意返回值、包名、类名、方法名、参数类型，
     * ..:表示任意参数类型，
     * 默认修饰符为public，可省略。
     */
//    @Before("execution(* com.itheima.service.impl.EmpServiceImpl.*(..))")
    @Before("execution(* com.itheima.service.impl.EmpServiceImpl.del*(..))")
    public void emp_del_pt(){}


    @Pointcut("execution(* com.itheima.service.impl.EmpServiceImpl.*(..))")
    public void emp_pt(){}

    @Pointcut("execution(* com.itheima.service.impl.DeptServiceImpl.*(..))")
    public void dept_pt(){}

    @Pointcut("execution(* com.itheima.service.impl.ClazzServiceImpl.*(..))")
    public void clazz_pt(){}

    @Pointcut("execution(* com.itheima.service.impl.StudentServiceImpl.*(..))")
    public void student_pt(){}

//    @Around("execution(* com.itheima.service.impl.DeptServiceImpl.*(..))")
//    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
//        //记录方法执行开始时间
//        long begin = System.currentTimeMillis();
//        //执行原始方法
//        Object result = pjp.proceed();//环绕通知返回结果必须是对象类型，否则报错
//        //记录方法执行结束时间
//        long end = System.currentTimeMillis();
//        //计算方法执行耗时
//        log.info("方法:{},执行耗时:{}毫秒",pjp.getSignature(),end-begin);
//        return result;
//    }

    /**
     * 除了@Around类新，别的通知类型需要使用JoinPoint参数，
     */
    @Before("execution(* com.itheima.service.EmpLogService.*(..))")
    public void before_pt(JoinPoint joinPoint){
        log.info("before_pt");
        //1.获取目标方法的参数
        Object target = joinPoint.getTarget();
        //2.获取目标方法的签名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("className:{}",className);
        //3.获取目标方法的签名
        String methodName = joinPoint.getSignature().getName();
        log.info("methodName:{}",methodName);
        //4.获取目标方法的参数
        Object[] args = joinPoint.getArgs();
        log.info("args:{}", Arrays.toString(args));

    }

}