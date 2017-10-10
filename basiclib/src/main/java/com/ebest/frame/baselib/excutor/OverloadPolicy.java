package com.ebest.frame.baselib.excutor;

/**
 * Created by ztw on 2017/9/29.
 */

public enum OverloadPolicy {
    DiscardNewTaskInQueue,
    DiscardOldTaskInQueue,
    DiscardCurrentTask,
    CallerRuns,
    ThrowExecption
}
