package com.watent.practice.vo;

import lombok.Getter;

import java.util.concurrent.Future;

/**
 * 并发题目处理时，返回处理的题目结果
 *
 * @author Atom
 */
@Getter
public class MultiQuestionVo {

    /**
     * 要么就是题目处理后的文本
     */
    private final String questionText;
    /**
     * 处理题目的任务
     */
    private final Future<QuestionCacheVo> questionFuture;

    public MultiQuestionVo(String questionText) {
        this.questionText = questionText;
        this.questionFuture = null;
    }

    public MultiQuestionVo(Future<QuestionCacheVo> questionFuture) {
        this.questionText = null;
        this.questionFuture = questionFuture;
    }
}
