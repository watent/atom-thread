package com.watent.practice.service.question;

import com.watent.practice.bizmock.Consts;
import com.watent.practice.bizmock.QuestionBank;
import com.watent.practice.vo.MultiQuestionVo;
import com.watent.practice.vo.QuestionCacheVo;
import com.watent.practice.vo.QuestionDBVo;

import java.util.concurrent.*;

/**
 * 并行异步的处理题目
 *
 * @author Atom
 */
public class QuestionMultiService {

    /**
     * 存放处理过题目内容的缓存
     * 考虑OOM
     * ConcurrentLinkedHashMap 限定最大容量 并实现一个了基于LRU也就是最近最少使用算法策略的进行更新的缓存
     */
    private static ConcurrentHashMap<Integer, QuestionCacheVo> questionCache = new ConcurrentHashMap<>();
    /**
     * 存放正在处理的题目的缓存，防止多个线程同时处理一个题目
     */
    private static ConcurrentHashMap<Integer, Future<QuestionCacheVo>> processingProblemCache = new ConcurrentHashMap<>();
    /**
     * 处理的题目的线程池
     */
    private static ExecutorService makeProblemExecPool = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE * 2);

    /**
     * 供调用者使用，返回题目的内容或者任务
     *
     * @param questionId 问题ID
     * @return
     */
    public static MultiQuestionVo makeProblem(Integer questionId) {

        //检查缓存中是否存在
        QuestionCacheVo problemCacheVo = questionCache.get(questionId);
        if (null == problemCacheVo) {
            System.out.println("题目【" + questionId + "】在缓存中不存在，需要新启任务");
            return new MultiQuestionVo(getQuestionFuture(questionId));
        } else {
            //拿摘要，一篇文档中的所有题目的摘要其实可以一次性取得，以减少对数据库的访问
            String problemSha = QuestionBank.getQuestionSha(questionId);
            if (problemCacheVo.getProblemSha().equals(problemSha)) {
                System.out.println("题目【" + questionId + "】在缓存中存在且没有修改过，可以直接使用。");
                return new MultiQuestionVo(problemCacheVo.getProcessedContent());
            } else {
                System.out.println("题目【" + questionId + "】的摘要发生了变化，启动任务更新缓存。");
                return new MultiQuestionVo(getQuestionFuture(questionId));
            }
        }
    }

    /**
     * 返回题目的工作任务
     *
     * @param questionId
     * @return
     */
    private static Future<QuestionCacheVo> getQuestionFuture(Integer questionId) {

        //校验正在进行中缓存任务
        Future<QuestionCacheVo> problemFuture = processingProblemCache.get(questionId);
        if (problemFuture == null) {
            QuestionDBVo problemDBVo = QuestionBank.getQuestion(questionId);
            ProblemTask problemTask = new ProblemTask(problemDBVo, questionId);
            //当前线程新启了一个任务
            FutureTask<QuestionCacheVo> ft = new FutureTask<>(problemTask);
            //A 进行中缓存存
            problemFuture = processingProblemCache.putIfAbsent(questionId, ft);
            //Note
            if (problemFuture == null) {
                //表示没有别的线程正在处理当前题目
                problemFuture = ft;
                makeProblemExecPool.execute(ft);
                System.out.println("题目【" + questionId + "】计算任务启动，请等待完成>>>>>>>>>>>>>。");
            } else {
                System.out.println("刚刚有其他线程启动了题目【" + questionId + "】的计算任务，任务不必开启");
            }
        } else {
            System.out.println("当前已经有了题目【" + questionId + "】的计算任务，不必重新开启");
        }
        return problemFuture;
    }

    /**
     * 处理题目的任务
     */
    private static class ProblemTask implements Callable<QuestionCacheVo> {

        private QuestionDBVo questionDBVo;
        private Integer questionId;

        public ProblemTask(QuestionDBVo questionDBVo, Integer questionId) {
            this.questionDBVo = questionDBVo;
            this.questionId = questionId;
        }

        @Override
        public QuestionCacheVo call() throws Exception {
            try {
                QuestionCacheVo questionCacheVo = new QuestionCacheVo();
                questionCacheVo.setProcessedContent(BaseQuestionService.makeQuestion(questionId, questionDBVo.getContent()));
                questionCacheVo.setProblemSha(questionDBVo.getSha());
                //处理完成任务缓存 放入
                questionCache.put(questionId, questionCacheVo);
                return questionCacheVo;
            } finally {
                //A 进行中缓存移除
                //无论正常还是异常，都需要将生成的题目的任务从缓存移除
                processingProblemCache.remove(questionId);
            }
        }
    }


}
