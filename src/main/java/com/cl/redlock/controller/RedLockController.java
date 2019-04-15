package com.cl.redlock.controller;

import com.cl.redlock.api.AquiredLockWorker;
import com.cl.redlock.api.impl.RedisLocke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/***
 * @ClassName: RedLockController
 * @Description:TODO
 * @author: chenling
 * @Date: 2019/4/15 10:14
 * @version : V1.0.0
 */
@Controller
public class RedLockController {


    @Autowired
    private RedisLocke distributedLocker;


    @RequestMapping(value = "/redlock")
    @ResponseBody
    public String testRedlock () throws  Exception{

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        // create and start threads
        for (int i = 0; i < 5; ++i) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        startSignal.countDown(); // let all threads proceed
        doneSignal.await();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }



   public class Worker implements Runnable {

        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

         public   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }


        @Override
        public void run() {
            try {
                startSignal.await();
                distributedLocker.lock("test", new AquiredLockWorker<Object>() {

                    @Override
                    public Object invokeAfterLockAquire() {
                        doTask();
                        return null;
                    }

                });
            } catch (Exception e) {

            }
        }

       public  void doTask() {
            System.out.println(Thread.currentThread().getName() + " start");
            Random random = new Random();
            int _int = random.nextInt(200);
            System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
            try {
                Thread.sleep(_int);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
            doneSignal.countDown();
        }
    }

}
