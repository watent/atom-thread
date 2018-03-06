package com.watent.practice.vo;

import lombok.Getter;

/**
 * 题目在数据库中存放实体类
 */
@Getter
public class QuestionDBVo {

    /**
     * 题目id
     */
    private final int problemId;

    /**
     * 题目内容，平均长度700字节
     */
    private final String content;

    /**
     * 题目的sha串
     */
    private final String sha;

    public QuestionDBVo(int problemId, String content, String sha) {
        this.problemId = problemId;
        this.content = content;
        this.sha = sha;
    }
}
