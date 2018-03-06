package com.watent.practice.service.question;

import com.watent.practice.bizmock.BusinessMock;

import java.util.Random;

/**
 * 题目处理的基础服务
 * 模拟解析题目文本 下载图片等操作
 * 返回解析后的文本
 *
 * @author Atom
 */
public class BaseQuestionService {

    /**
     * 处理问题
     *
     * @param questionId  问题ID
     * @param questionSrc 问题
     * @return 处理后问文本
     */
    public static String makeQuestion(Integer questionId, String questionSrc) {

        Random r = new Random();
        BusinessMock.business(450 + r.nextInt(100));

        return "CompleteProblem[id=" + questionId + " content=:" + questionSrc + "]";
    }

}
