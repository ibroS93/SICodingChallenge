package application;

import application.model.Question;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QuestionInputParserTest {
    private QuestionInputParser parser = new QuestionInputParser();

    @Test
    void whenValidInput_ReturnsQuestion() {
        String input = "What is Peters favorite food? \"Pizza\" \"Spaghetti\" \"Ice cream\"";
        Question question = parser.parseQuestion(input);

        assertEquals("What is Peters favorite food?", question.getText());
        assertEquals(List.of("Pizza", "Spaghetti", "Ice cream"), question.getAnswers());
    }

    @Test
    void whenInvalidInputFormat_ThrowsException() {
        String input = "What is Peters favorite food";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseQuestion(input);
        });
        assertEquals("Please ensure the question and answers are separated by a '?' character.", exception.getMessage());
    }

    @Test
    void whenQuestionTooLong_ThrowsException() {
        String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaaa etstsetss?" +
                " \"Paris\"";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseQuestion(input);
        });
        assertEquals("Question length exceeds the maximum allowed length of 255 characters.", exception.getMessage());

    }

    @Test
    void whenNoAnswersProvided_ThrowsException() {
        String input = "What is Peters favorite food? ";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseQuestion(input);
        });
        assertEquals("Answers should be enclosed in double quotes and separated by a space, e.g., \"answer1\" \"answer2\".", exception.getMessage());
    }

    @Test
    void whenAnswersFormatIsInvalid_ThrowsException() {
        String input = "What is Peters favorite food? \"Pizza\" \"Spaghetti";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseQuestion(input);
        });
        assertEquals("Answers should be enclosed in double quotes and separated by a space, e.g., \"answer1\" \"answer2\".", exception.getMessage());
    }

    @Test
    void whenAnswerTooLong_ThrowsException() {
        String input = "What is Peters favorite food? \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua sddsfsdfsd sddsfsdfsdfsd\"";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseQuestion(input);
        });
        assertEquals("Answer length exceeds the maximum allowed length of 255 characters.", exception.getMessage());
    }
}
