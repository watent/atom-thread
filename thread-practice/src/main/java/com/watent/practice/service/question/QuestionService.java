package com.watent.practice.service.question;


import com.watent.practice.bizmock.QuestionBank;

public class QuestionService {

    public static String makeProblem(Integer questionId) {

        return BaseQuestionService.makeQuestion(questionId, QuestionBank.getQuestion(questionId).getContent());

    }

}
