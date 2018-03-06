package com.watent.practice.bizmock;


import com.watent.practice.vo.QuestionDBVo;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 题库的模拟
 *
 * @author Atom
 */
public class QuestionBank {

    /**
     * 题库数据存储
     */
    private static ConcurrentHashMap<Integer, QuestionDBVo> questionBankMap = new ConcurrentHashMap<>();

    /**
     * 定时任务池，负责定时更新题库数据
     */
    private static ScheduledExecutorService updateQuestionBankPool = new ScheduledThreadPoolExecutor(1);

    /**
     * 初始化题库
     */
    public static void initBank() {
        for (int i = 0; i < Consts.QUESTION_BANK_COUNT; i++) {
            String problemContent = getRandomString(700);
            questionBankMap.put(i, new QuestionDBVo(i, problemContent, EncryptTools.EncryptBySHA1(problemContent)));
        }
        updateProblemTimer();
    }

    /**
     * 生成随机字符串
     * length表示生成字符串的长度
     *
     * @param length 长度
     * @return 字符串
     */
    private static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获得题目，我们假设一次数据库的读耗时在一般在20ms左右，所以休眠20ms
     *
     * @param id 问题ID
     * @return 问题
     */
    public static QuestionDBVo getQuestion(int id) {
        BusinessMock.business(20);
        return questionBankMap.get(id);
    }

    public static String getQuestionSha(int i) {
        BusinessMock.business(10);
        return questionBankMap.get(i).getSha();
    }

    /**
     * 更新题库的定时任务
     */
    private static class UpdateProblem implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            int questionId = random.nextInt(Consts.QUESTION_BANK_COUNT);
            String questionContent = getRandomString(700);
            questionBankMap.put(questionId, new QuestionDBVo(questionId, questionContent, EncryptTools.EncryptBySHA1(questionContent)));
            //System.out.println("题目【"+problemId+"】被更新！！");
        }
    }

    /**
     * 定期更新题库数据
     */
    private static void updateProblemTimer() {

        System.out.println("开始定时更新题库..........................");
        updateQuestionBankPool.scheduleAtFixedRate(new UpdateProblem(), 15, 5, TimeUnit.SECONDS);
    }

}
