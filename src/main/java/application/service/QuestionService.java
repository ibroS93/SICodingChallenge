package application.service;

import application.model.Question;

public interface QuestionService {
    Question getQuestionByText(String text);

    void addQuestion(Question question);
}
