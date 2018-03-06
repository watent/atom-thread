package com.watent.thread.principle.future.mode;

import com.watent.thread.base.util.SleepUtils;

/**
 * 真实数据
 */
public class RealData implements Data {

    protected final String result;

    public RealData(String param) {
        //模拟长时间调用
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            buffer.append(param).append(i);
            SleepUtils.second(1);
        }
        result = buffer.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
