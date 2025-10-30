package cn.com.xuroot.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ExamBo {
    private Integer examId;
    private String examName;
    private Date startTime;
    private Date endTime;
    private String status;
    private Integer createId;
    private Date createTime;
    private Integer chapterId;
    private Integer totalScore;
    private String examInstruct;
    private Integer singleChoiceCount;
    private Integer multipleChoiceCount;
    private Integer judgeCount;
    private Integer testExamTime;
    private Integer difficult;
    private Integer classId;
    private Integer paperId;
}