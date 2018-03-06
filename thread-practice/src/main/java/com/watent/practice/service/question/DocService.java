package com.watent.practice.service.question;

import com.watent.practice.bizmock.BusinessMock;
import com.watent.practice.vo.MultiQuestionVo;
import com.watent.practice.vo.PendingDocVo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * 文档处理服务
 *
 * @author Atom
 */
public class DocService {

    /**
     * 上传文档到网络
     *
     * @param docFileName 实际文档在本地的存储位置
     * @return 上传后的网络存储地址
     */
    public static String upLoadDoc(String docFileName) {

        Random r = new Random();
        BusinessMock.business(5000 + r.nextInt(400));
        return "http://www.xxxx.com/file/upload/" + docFileName;
    }

    /**
     * 将待处理文档处理为本地实际文档
     *
     * @param pendingDocVo 待处理文档
     * @return 实际文档在本地的存储位置
     */
    public static String makeDoc(PendingDocVo pendingDocVo) {

        System.out.println("开始处理文档：" + pendingDocVo.getDocName());
        StringBuffer sb = new StringBuffer();
        for (Integer problemId : pendingDocVo.getProblemVoList()) {
            sb.append(QuestionService.makeProblem(problemId));
        }
        return "complete_" + System.currentTimeMillis() + "_" + pendingDocVo.getDocName() + ".pdf";
    }

    /**
     * 异步并行处理文档中的题目
     *
     * @param pendingDocVo
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String makeAsync(PendingDocVo pendingDocVo) throws ExecutionException, InterruptedException {
        System.out.println("开始处理文档：" + pendingDocVo.getDocName());

        //对题目处理结果的缓存
        Map<Integer, MultiQuestionVo> multiProblemVoMap = new HashMap<>(16);
        //并行处理文档中的每个题目
        for (Integer problemId : pendingDocVo.getProblemVoList()) {
            multiProblemVoMap.put(problemId, QuestionMultiService.makeProblem(problemId));
        }

        //获取题目的结果
        StringBuilder buffer = new StringBuilder();
        for (Integer problemId : pendingDocVo.getProblemVoList()) {
            MultiQuestionVo multiQuestionVo = multiProblemVoMap.get(problemId);
            buffer.append(
                    multiQuestionVo.getQuestionText() == null
                            ? multiQuestionVo.getQuestionFuture().get().getProcessedContent()
                            : multiQuestionVo.getQuestionText()
            );
        }
        return "complete_" + System.currentTimeMillis() + "_" + pendingDocVo.getDocName() + ".pdf";

    }

}
