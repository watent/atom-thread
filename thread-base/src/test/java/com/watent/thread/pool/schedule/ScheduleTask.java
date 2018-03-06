package com.watent.thread.pool.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleTask implements Runnable {

    public static enum OperateType {
        None, OnlyThrowException, CatheException
    }

    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OperateType operateType;

    public ScheduleTask(OperateType operateType) {
        this.operateType = operateType;
    }

    @Override
    public void run() {

        switch (operateType) {
            case OnlyThrowException:
                System.out.println("Exception not catch:" + formater.format(new Date()));
                throw new RuntimeException("OnlyThrowException");
            case CatheException:
                try {
                    throw new RuntimeException("CatheException");
                } catch (RuntimeException e) {
                    System.out.println("Exception be catched:" + formater.format(new Date()));
                }
                break;
            case None:
                System.out.println("None :" + formater.format(new Date()));
        }
    }
}
