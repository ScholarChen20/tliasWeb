package com.itheima.utils;

public class CurrentHolder {
    /**
     * ThreadLocal其实是线程的局部变量，为每个线程提供单独一份存储空间，具有线程隔离的效果
     * 应用场景：同一个线程/同一个请求时 需要实现数据共享
     */

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}