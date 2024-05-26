package application;

import application.model.Question;

import java.util.*;

public class QuestionInputParser {

    private static final int MAX_LENGTH = 255;

    public Question parseQuestion(String input) {
        String[] questionAndAnswers = parseToQuestionAndAnswers(input);
        String questionPart = questionAndAnswers[0].trim();
        String questionText = questionPart + "?";
        if (questionText.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Question length exceeds the maximum allowed length of " + MAX_LENGTH + " characters.");
        }
        String answersPart = questionAndAnswers[1].trim();
        List<String> answers = parseAnswers(answersPart);

        return new Question(questionText, answers);
    }

    private String[] parseToQuestionAndAnswers(String input) {
        String[] parts = input.split("\\?");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Please ensure the question and answers are separated by a '?' character.");
        }

        return parts;
    }

    private List<String> parseAnswers(String answersInput) {
        int numberOfQuotationMarks = 0;
        for (int i = 0; i < answersInput.length(); i++) {
            if (answersInput.charAt(i) == '"') {
                numberOfQuotationMarks++;
            }
        }
        if (numberOfQuotationMarks == 0 || numberOfQuotationMarks % 2 != 0) {
            throw new IllegalArgumentException("Answers should be enclosed in double quotes and separated by a space, e.g., \"answer1\" \"answer2\".");
        }
        List<String> answers = new ArrayList<>();
        int numberOfAnswers = numberOfQuotationMarks / 2;
        for (int i = 0; i < numberOfAnswers; i++) {
            int firstIndex = answersInput.indexOf("\"");
            int secondIndex = answersInput.indexOf("\"", firstIndex + 1);
            String answer = answersInput.substring(firstIndex + 1, secondIndex);
            if (answer.length() > MAX_LENGTH) {
                throw new IllegalArgumentException("Answer length exceeds the maximum allowed length of " + MAX_LENGTH + " characters.");
            }
            answers.add(answer);
            answersInput = answersInput.substring(secondIndex + 1);
        }

        return answers;
    }

}
