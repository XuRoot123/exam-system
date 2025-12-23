package cn.com.xuroot.bo;

import lombok.Data;

import java.util.Date;

@Data
public class StudentAnswersBo {
    private Integer recordId;
    private Integer examId;
    private Integer studentId;
    private Integer[] questionsId;
    private String[] answers;
    private Integer[] isCorrect;
    private Integer isReviewed;
    private Integer[] score;
    private String reviewedBy;
    private String reviewNotes;
    private Date createdAt;
}
