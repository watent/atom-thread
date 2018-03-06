package com.watent.thread.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangeCase {

    private static final Exchanger<List<String>> exgr = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(Thread.currentThread().getId() + " insert A1");
                    list.add(Thread.currentThread().getId() + " insert A2");
                    list = exgr.exchange(list);//交换数据
                    for (String item : list) {
                        System.out.println(Thread.currentThread().getId() + ":" + item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(Thread.currentThread().getId() + " insert B1");
                    list.add(Thread.currentThread().getId() + " insert B2");
                    list.add(Thread.currentThread().getId() + " insert B3");
                    System.out.println(Thread.currentThread().getId() + " will sleep");
                    Thread.sleep(1500);
                    list = exgr.exchange(list);//交换数据
                    for (String item : list) {
                        System.out.println(Thread.currentThread().getId() + ":" + item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
