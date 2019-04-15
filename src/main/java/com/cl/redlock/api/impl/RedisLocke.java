package com.cl.redlock.api.impl;

import com.cl.redlock.api.AquiredLockWorker;
import com.cl.redlock.api.DistributedLocker;
import com.cl.redlock.exception.UnableToAquireLockException;
import com.cl.redlock.redission.RedissonConnector;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/***
 * @ClassName: RedisLocke
 * @Description:TODO
 * @author: chenling
 * @Date: 2019/4/12 16:37
 * @version : V1.0.0
 */
@Component
public class RedisLocke implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    RedissonConnector redissonConnector;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName, worker, 100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }

}
