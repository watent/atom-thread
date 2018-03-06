package com.watent.thread.base.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 */
public class RwNumImpl implements IGoodsNum {

    private GoodsVo goods;

    private final  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final  Lock r = lock.readLock();
    private final  Lock w = lock.writeLock();

    public RwNumImpl(GoodsVo goods) {
        this.goods = goods;
    }


    @Override
    public GoodsVo getGoodsNumber() {
        r.lock();
        try{
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.goods;
        }finally{
            // finally 不然异常时不会释放锁
            r.unlock();
        }
    }

    @Override
    public void setGoodsNumber(int changeNumber) {
        w.lock();
        try{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.goods.setGoodsVoNumber(changeNumber);
        }finally{
            w.unlock();
        }
    }
}
