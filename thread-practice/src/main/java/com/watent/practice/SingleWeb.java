package com.watent.practice;

import com.watent.practice.bizmock.MakeSrcDoc;
import com.watent.practice.bizmock.QuestionBank;
import com.watent.practice.service.question.DocService;
import com.watent.practice.vo.PendingDocVo;

import java.util.List;

/**
 * @author Atom
 */
public class SingleWeb {

    public static void main(String[] args) {

        System.out.println("题库开始初始化...........");
        QuestionBank.initBank();
        System.out.println("题库初始化完成。");

        List<PendingDocVo> docList = MakeSrcDoc.makeDoc(2);
        long startTotal = System.currentTimeMillis();
        for (PendingDocVo doc : docList) {
            System.out.println("开始处理文档：" + doc.getDocName() + ".......");
            long start = System.currentTimeMillis();
            String localName = DocService.makeDoc(doc);
            System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");

            start = System.currentTimeMillis();
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");
    }

}
