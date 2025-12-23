package cn.com.xuroot.vo.exam;

import lombok.Data;

@Data
public class ExamsVo {
    private Integer examId;
    private Integer paperId;
    private String paperName;
    private String status;
    private String instructions;
    private String startTime;
    private String endTime;
    private String createdAt;
    private Integer totalScore;
    private Integer testExamTime;
    private Integer chapterId;
    private Integer classId;
    private String teacherName;
}
