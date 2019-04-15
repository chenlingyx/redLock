package com.cl.redlock.exception;

/***
 * @ClassName: UnableToAquireLockException
 * @Description:TODO 异常类
 * @author: chenling
 * @Date: 2019/4/12 16:30
 * @version : V1.0.0
 */
public class UnableToAquireLockException  extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
