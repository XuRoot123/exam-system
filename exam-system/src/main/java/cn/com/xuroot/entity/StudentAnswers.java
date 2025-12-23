package cn.com.xuroot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StudentAnswers {
    private Integer recordId;
    private Integer examId;
    private Integer studentId;
    private Integer questionId;
    private String answer;
    private Integer isCorrect;
    private Integer isReviewed;
    private Integer score;
    private String reviewedBy;
    private String reviewNotes;
    private Date createdAt;
}
