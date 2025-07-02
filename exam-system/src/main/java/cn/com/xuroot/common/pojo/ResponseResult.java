package cn.com.xuroot.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(title = "通用响应结果类")
@Data
public class ResponseResult<T> {
    @Schema(title = "状态码",description = "请求时正常返回：0，失败返回：-1")
    private int code;
    // 1001: 登录中用户名错误， 1002: 登录中密码错误
    // 2001: 用户模块操作中用户名错误， 2002: 用户模块操作中用户名重复
    @Schema(title = "提示消息",description = "跟code对应的提示信息：0：success, -1:系统错误")
    private String msg;

    @Schema(title = "响应实际数据",description = "操作成功时，会有请求对应的响应数据；失败时，为null")
    private T data;

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(0, "success", data);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<T>(code, msg, null);
    }
}
