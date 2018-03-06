package com.watent.practice.bizmock;

/**
 * 用sleep模拟实际的业务操作
 *
 * @author Atom
 */
public class BusinessMock {

    public static void business(Integer sleepTime) {

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
