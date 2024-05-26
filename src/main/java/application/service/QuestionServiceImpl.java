package application.service;

import application.model.Question;

import java.util.HashMap;
import java.util.Map;


public class QuestionServiceImpl implements QuestionService {

    private static final Map<String, Question> questionsMap = new HashMap<>();

    @Override
    public Question getQuestionByText(String text) {
        return questionsMap.get(text);
    }

    @Override
    public void addQuestion(Question question) {
        questionsMap.put(question.getText(), question);
    }
}
