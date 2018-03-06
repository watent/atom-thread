package com.watent.thread.principle.future.jdk;

import com.watent.thread.base.util.SleepUtils;

import java.util.concurrent.Callable;

/**
 * 真实数据
 */
public class RealData implements Callable<String> {

    private String param;

    public RealData(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        //模拟长时间调用
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            buffer.append(param).append(i);
            SleepUtils.second(1);
        }
        return buffer.toString();
    }


}
