package application;

import application.model.Question;
import application.service.QuestionService;
import application.service.QuestionServiceImpl;

import java.util.Scanner;

public class QuizManager {

    private final QuestionService questionService = new QuestionServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final QuestionInputParser questionInputParser = new QuestionInputParser();

    private static final String DEFAULT_ANSWER = "the answer to life, universe and everything is 42";

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            try {
                displayMenuOptions();

                int userChoice = getUserOption();

                isRunning = processUserChoice(userChoice);
            } catch (Exception ex) {

                System.out.println("Invalid input format. " + ex.getMessage());
            }
        }
    }

    private void displayMenuOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Ask a question");
        System.out.println("2. Add a question and answers");
        System.out.println("3. Exit");
    }

    public int getUserOption() {
        try {
            String userInput = scanner.nextLine();
            return Integer.parseInt(userInput);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid number.");
        }
    }

    private boolean processUserChoice(int choice) {

        switch (choice) {
            case 1:
                askQuestion();
                return true;
            case 2:
                addQuestionAndAnswers();
                return true;
            case 3:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    public void askQuestion() {
        System.out.println("Enter your question:");
        String question = scanner.nextLine();
        showAnswers(question);
    }

    private void showAnswers(String questionText) {

        Question question = questionService.getQuestionByText(questionText);
        if (question != null) {

            for (String answer : question.getAnswers()) {
                System.out.println(answer);
            }
        } else {

            System.out.println(DEFAULT_ANSWER);
        }
    }

    private void addQuestionAndAnswers() {

        System.out.println("Enter your question followed by answers in the format:");
        System.out.println("<question>? \"<answer1>\" \"<answer2>\" ...");


        String input = scanner.nextLine();


        Question question = questionInputParser.parseQuestion(input);


        questionService.addQuestion(question);


        System.out.println("Question and answers added successfully.");
    }

}
