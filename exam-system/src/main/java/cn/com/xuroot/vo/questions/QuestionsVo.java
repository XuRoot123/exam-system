package cn.com.xuroot.vo.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import java.util.List;

@Data
public class QuestionsVo {
    private Integer questionId;
    private Integer chapterId;
    private String content;
    private String type;
    @JsonIgnore
    private String options;
    private String answer;
    private Integer baseScore;
    private String explanation;
    private String difficulty;
    private String createdBy;
    private String createdAt;

    // 辅助字段，用于接收数组类型的options
    @JsonProperty("options")
    private List<String> optionsList;

    // 转换方法，将optionsList转换为JSON字符串存储在options中
    public void convertOptionsListToString() {
        if (optionsList != null && !optionsList.isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                this.options = mapper.writeValueAsString(optionsList);
            } catch (Exception e) {
                // 处理转换异常
                this.options = "[]"; // 默认空数组
            }
        }
    }
}