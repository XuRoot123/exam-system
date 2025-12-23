package cn.com.xuroot.vo.exam;

import lombok.Data;

import java.util.Date;

@Data
public class ExamHistoryVo {
    private Integer userId;
    private Integer examId;
    private String paperName;
    private Integer totalScore;
    private Integer actualScore;
    private String examAt;
}
