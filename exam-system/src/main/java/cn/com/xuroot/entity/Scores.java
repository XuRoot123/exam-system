package cn.com.xuroot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Scores {
    private Integer scoreId;
    private Integer examId;
    private Integer studentId;
    private Integer totalScore;
    private Integer objectiveScore;
    private Integer subjectiveScore;
    private Integer ranking;
    private Date evaluatedAt;
}
