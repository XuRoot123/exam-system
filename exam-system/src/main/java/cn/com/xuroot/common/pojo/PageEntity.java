package cn.com.xuroot.common.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageEntity<T> {
    private Integer total;
    private List<T> list;

}
