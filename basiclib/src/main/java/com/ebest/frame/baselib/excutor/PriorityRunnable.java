package com.ebest.frame.baselib.excutor;

/**
 * Created by ztw on 2017/9/29.
 */

public abstract class PriorityRunnable implements Runnable {

    int priority;

    protected PriorityRunnable(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}