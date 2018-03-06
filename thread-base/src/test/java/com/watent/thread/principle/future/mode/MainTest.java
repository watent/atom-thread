package com.watent.thread.principle.future.mode;

import com.watent.thread.base.util.SleepUtils;

public class MainTest {

    public static void main(String[] args) {

        Client client = new Client();
        Data data = client.request("name ");
        System.out.println("请求完毕");
        SleepUtils.second(2);
        System.out.println("数据:" + data.getResult());
    }

}
