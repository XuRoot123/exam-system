package cn.com.xuroot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Exams {
    private Integer examId;
    private Integer paperId;
    private Integer classId;
    private Date startTime;
    private Date endTime;
    private String status;
    private String instructions;
}
