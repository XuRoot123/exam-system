package cn.com.xuroot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ExamPapers {
    private Integer paperId;
    private String paperName;
    private Integer teacherId;
    private Integer chapterId;
    private Integer totalScore;
    private Integer durationMin;
    private String status;
    private Date createdAt;
}
