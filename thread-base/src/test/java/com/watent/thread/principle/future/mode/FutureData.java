package com.watent.thread.principle.future.mode;

/**
 * 包装数据
 */
public class FutureData implements Data {

    protected RealData realData;

    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }

    @Override
    public synchronized String getResult() {

        while (!isReady) {

            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
        return realData.getResult();

    }
}
