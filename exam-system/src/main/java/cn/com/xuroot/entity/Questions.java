package cn.com.xuroot.entity;

import lombok.Data;

@Data
public class Questions {
    private Integer question_id;
    private Integer chapter_id;
    private String content;
    private String type;
    private String options;
    private String answer;
    private Integer base_score;
    private String explanation;
    private String difficulty;
    private String created_by;
    private String created_at;
}
