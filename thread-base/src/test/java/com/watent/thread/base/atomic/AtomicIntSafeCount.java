package com.watent.thread.base.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class AtomicIntSafeCount {
    private AtomicInteger atomicI = new AtomicInteger(0);

    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

}
