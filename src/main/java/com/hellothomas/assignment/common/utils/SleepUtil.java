package com.hellothomas.assignment.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static com.hellothomas.assignment.common.enums.ErrorCodeEnum.THREAD_SLEEP_ERROR;

/**
 * @classname SleepUtil
 * @author Thomas
 * @date 2020/11/4 15:32
 * @description
 * @version 1.0
 */
@Slf4j
public class SleepUtil {
    public static final void millisecond(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.warn(THREAD_SLEEP_ERROR.getMessage(), e);
        }
    }
}
