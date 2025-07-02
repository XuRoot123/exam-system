package cn.com.xuroot.common.advice;
import cn.com.xuroot.common.exception.BizException;
import cn.com.xuroot.common.pojo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true ));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(-1,"系统异常,请联系管理员");
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseResult<Void> handleException(BindException e) {
        log.warn(e.getMessage(), e);
        List<ObjectError> allErrors = e.getAllErrors();
        StringJoiner errors = new StringJoiner(";");
        for (ObjectError error : allErrors) {
            errors.add(error.getDefaultMessage());
        }
        return ResponseResult.error(-1,errors.toString());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseResult<Void> handleException(MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage(), e);
        return ResponseResult.error(-1,"参数类型不匹配，请填写正确的参数。");
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseResult<Void> handleException(BizException e) {
        log.warn(e.getMessage(), e);
        return ResponseResult.error(e.getCode(),e.getMessage());
    }
}
