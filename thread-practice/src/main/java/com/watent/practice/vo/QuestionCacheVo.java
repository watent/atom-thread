package com.watent.practice.vo;

import lombok.Data;

/**
 * 保存在缓存实体封装
 */
@Data
public class QuestionCacheVo {

    /**
     * 题目内容
     */
    private String processedContent;

    /**
     * 内容加密sha  same 版本号 时间戳
     */
    private String problemSha;


}
