package com.watent.practice.bizmock;

import com.watent.practice.vo.PendingDocVo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Atom
 */
public class MakeSrcDoc {

    /**
     * 形成待处理文档
     *
     * @param docCount 生成的文档数量
     * @return 待处理文档列表
     */
    public static List<PendingDocVo> makeDoc(int docCount) {

        Random r = new Random();
        Random rProblemCount = new Random();
        //文档列表
        List<PendingDocVo> docList = new ArrayList<>();
        for (int i = 0; i < docCount; i++) {
            //文档中题目列表
            List<Integer> problemList = new LinkedList<>();
            //60+
            int docProblemCount = rProblemCount.nextInt(60) + 60;
            for (int j = 0; j < docProblemCount; j++) {
                int problemId = r.nextInt(Consts.QUESTION_BANK_COUNT);
                problemList.add(problemId);
            }
            PendingDocVo pendingDocVo = new PendingDocVo("pending_" + i, problemList);
            docList.add(pendingDocVo);
        }
        return docList;
    }
}
