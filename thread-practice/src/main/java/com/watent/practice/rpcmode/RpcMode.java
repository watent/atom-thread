package com.watent.practice.rpcmode;

import com.watent.practice.bizmock.Consts;
import com.watent.practice.bizmock.MakeSrcDoc;
import com.watent.practice.bizmock.QuestionBank;
import com.watent.practice.service.question.DocService;
import com.watent.practice.vo.PendingDocVo;

import java.util.List;
import java.util.concurrent.*;

/**
 * rpc服务 服务的拆分
 *
 * @author Atom
 */
public class RpcMode {

    /**
     * 生成文档的线程池
     */
    private static ExecutorService docMakeService
            = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);

    /**
     * 上传文档的线程池
     */
    private static ExecutorService docUploadService
            = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);

    /**
     * CompletionService 先完成的先返回
     */
    private static CompletionService docCompletionService = new ExecutorCompletionService(docMakeService);
    private static CompletionService uploadCompletionService = new ExecutorCompletionService(docUploadService);


    private static class MakeDocTask implements Callable<String> {

        private PendingDocVo pendingDocVo;

        public MakeDocTask(PendingDocVo pendingDocVo) {
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {

            long start = System.currentTimeMillis();
            String localName = DocService.makeAsync(pendingDocVo);
            System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");
            return localName;
        }
    }

    private static class UploadDocTask implements Callable<String> {
        private String localName;

        public UploadDocTask(String localName) {
            this.localName = localName;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时："
                    + (System.currentTimeMillis() - start) + "ms");
            return remoteUrl;
        }
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        System.out.println("题库开始初始化...........");
        QuestionBank.initBank();
        System.out.println("题库初始化完成。");

        List<PendingDocVo> docList = MakeSrcDoc.makeDoc(60);

        long startTotal = System.currentTimeMillis();

        for (PendingDocVo doc : docList) {
            docCompletionService.submit(new MakeDocTask(doc));
        }

        for (PendingDocVo doc : docList) {
            Future<String> futureLocalName = docCompletionService.take();
            uploadCompletionService.submit(new UploadDocTask(futureLocalName.get()));
        }

        for (PendingDocVo doc : docList) {
            //把上传后的网络存储地址拿到
            uploadCompletionService.take().get();
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");

        //117887ms
        //125338ms
    }

}
