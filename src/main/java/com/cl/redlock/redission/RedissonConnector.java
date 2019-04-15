package com.cl.redlock.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/***
 * @ClassName: RedissonConnector
 * @Description:TODO 获取RedissonClient连接类
 * @author: chenling
 * @Date: 2019/4/12 16:33
 * @version : V1.0.0
 */
@Component
public class RedissonConnector {


   private RedissonClient redisson;

   @PostConstruct
   public void  init(){
       redisson = Redisson.create();
   }

    public RedissonClient getClient(){
        return redisson;
    }
}
