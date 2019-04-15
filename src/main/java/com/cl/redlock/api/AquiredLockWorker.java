package com.cl.redlock.api;

/***
 * @ClassName: AquiredLockWorker
 * @Description:TODO 获取锁后需要处理的逻辑
 * @author: chenling
 * @Date: 2019/4/12 16:26
 * @version : V1.0.0
 */
public interface  AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;

}
