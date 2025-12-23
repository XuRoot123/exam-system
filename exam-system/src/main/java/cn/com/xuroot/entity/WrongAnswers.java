package cn.com.xuroot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WrongAnswers {
    private Integer wrongId;
    private Integer studentId;
    private Integer questionId;
    private Integer examId;
    private String wrongAnswer;
    private String correctAnswer;
    private Date collectedAt;
}
