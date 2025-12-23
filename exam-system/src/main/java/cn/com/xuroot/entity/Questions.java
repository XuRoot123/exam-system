package cn.com.xuroot.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
public class Questions {
    private Integer questionId;
    private Integer chapterId;
    private String content;
    private String type;
    private String options;
    private String answer;
    private Integer baseScore;
    private String explanation;
    private String difficulty;
    private String createdBy;
    private String createdAt;
    public JsonNode getOptionsAsJsonNode() throws IOException {
        if (options != null && !options.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(options);
        }
        return null;
    }

    public void setOptionsFromJsonNode(JsonNode optionsNode) {
        if (optionsNode != null) {
            this.options = optionsNode.toString();
        } else {
            this.options = null;
        }
    }
}
