package cn.com.xuroot.bo;

import lombok.Data;

import java.util.Date;

@Data
public class WrongAnswersBo {
    private Integer wrongId;
    private Integer studentId;
    private Integer[] questionsId;
    private Integer examId;
    private String[] wrongAnswers;
    private String[] correctAnswers;
    private Date collectedAt;
}
