package com.cl.redlock.api;

import com.cl.redlock.exception.UnableToAquireLockException;

/***
 * @ClassName: DistributedLocker
 * @Description:TODO 获取管理类
 * @author: chenling
 * @Date: 2019/4/12 16:27
 * @version : V1.0.0
 */
public interface DistributedLocker {


    /**
    *
    * @author chenling
    * @description //TODO
    * @date 16:28 2019/4/12
     * 获取锁
     * @param resourceName  锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
    * @return T
    **/
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;
}
