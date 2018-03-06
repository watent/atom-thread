package com.watent.thread.base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *
 7:JDWP Command Reader
 6:JDWP Event Helper Thread             JDWP是通讯交互协议 将JDI事件映射成JVMTI信号，以达到调试过程中操作JVM的目的
 5:JDWP Transport Listener: dt_socket   Java Debugger的监听器线程
 4:Signal Dispatcher                    //将信号分给jvm的线程
 3:Finalizer                            //调用对象的finalizer 方法
 2:Reference Handler                    //清除Reference
 1:main                                 //程序的主入口
 *
 */
public class MainThreadTest {

    public static void main(String[] args) {
        //java虚拟机的线程管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //获取线程信息
        ThreadInfo[] threadInfoAry = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfoAry) {
            System.out.println(threadInfo.getThreadId() + ":" + threadInfo.getThreadName());
        }
    }

}
