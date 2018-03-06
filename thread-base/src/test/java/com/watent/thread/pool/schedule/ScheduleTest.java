package com.watent.thread.pool.schedule;

import com.watent.thread.base.util.SleepUtils;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * scheduleAtFixedRate          延迟后 固定周期执行一次    start->start
 * scheduleWithFixedDelay       延迟后 固定延迟执行一次    end->start
 */
public class ScheduleTest {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        /**
         * 每隔一段时间打印系统时间，互不影响的创建并执行一个在给定初始延迟后首次启用的定期操作，
         * 后续操作具有给定的周期；
         * 也就是将在 initialDelay 后开始执行，周期为period。
         */
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperateType.None), 1000, 5000, TimeUnit.MILLISECONDS);

        // 开始执行后就触发异常,next周期将不会运行
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperateType.OnlyThrowException), 1000, 5000, TimeUnit.MILLISECONDS);

        // 虽然抛出了运行异常,当被拦截了,next周期继续运行
        exec.scheduleAtFixedRate(new ScheduleTask(ScheduleTask.OperateType.CatheException), 1000, 5000, TimeUnit.MILLISECONDS);

        /**
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，
         * 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
         */
        exec.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                System.out.println("scheduleWithFixedDelay:begin" + ScheduleTask.formater.format(new Date()));
                SleepUtils.second(2);
                System.out.println("scheduleWithFixedDelay:end" + ScheduleTask.formater.format(new Date()));

            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);

        /**
         * 创建并执行在给定延迟后启用的一次性操作。
         */
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("schedule running.....");
            }
        }, 5000, TimeUnit.MILLISECONDS);

    }
}
