package cn.com.xuroot.vo.questions;

import lombok.Data;

@Data
public class HotQuestionVo {
    private Integer questionId;
    private String content;
    private String type;
    private String difficulty;
    private Integer chapterId;
    private Long appearCount;
}
