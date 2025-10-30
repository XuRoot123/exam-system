package cn.com.xuroot.entity;

import lombok.Data;

@Data
public class PaperQuestions {
    private Integer relationId;
    private Integer paperId;
    private Integer questionId;
    private Integer questionScore;
    private Integer questionOrder;
}
