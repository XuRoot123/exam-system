package cn.com.xuroot.entity;

import lombok.Data;

@Data
public class Chapter {
    private Integer chapterId;
    private String courseName;
    private String chapterName;
    private Integer chapterOrder;
    private String description;
}
