package com.watent.thread.base.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class AtomicIntTest {
    static AtomicInteger ai = new AtomicInteger(1);
    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        ai.incrementAndGet();
        System.out.println(ai.get());
    }
}
