package com.bai.guli.service.base.handel;

import com.bai.guli.common.base.result.R;
import com.bai.guli.common.base.result.ResultCode;
import com.bai.guli.common.base.util.ExceptionUtils;
import com.bai.guli.service.base.exception.CollegeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GobalExceptionHandel {

    /**
     * 最大范围的异常信息抛出
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R error(Exception e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R error(BadSqlGrammarException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCode.BAD_SQL_GRAMMAR);
    }

    /**
     * json信息  返回异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R error(HttpMessageNotReadableException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCode.JSON_PARSE_ERROR);
    }

    /**
     * 返回对应的状态吗
     * @param e
     * @return
     */
    @ExceptionHandler(value = CollegeException.class)
    public R error(CollegeException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
