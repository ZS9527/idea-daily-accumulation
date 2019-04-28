package com.test.testidea.config;

import com.test.testidea.basic.Result;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局错误处理
 *
 * @author fangzhimin
 * @date 2017年11月30日 上午11:36:31
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     * @param e 异常信息
     * @return 统一的校验失败信息 {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public Result bindErrorHandler(Exception e) {
        String error = "bad request.";

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            error = ex.getConstraintViolations().iterator().next().getMessage();
        } else {
            BindingResult result = null;
            if (e instanceof MethodArgumentNotValidException) {
                result = ((MethodArgumentNotValidException) e).getBindingResult();
            } else if (e instanceof BindException) {
                result = ((BindException) e).getBindingResult();
            }

            if (result != null) {
                error = result.getAllErrors().iterator().next().getDefaultMessage();
            }
        }

        return Result.no(error);
    }

    /**
     * 登录授权认证异常
     * @param e 异常信息
     * @return 异常信息反馈 {@link HttpStatus#NON_AUTHORITATIVE_INFORMATION}
     */
    @ExceptionHandler(value = { AuthenticationException.class, BadCredentialsException.class })
    public Result authErrorHandler(Exception e) {
        return Result.no(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), e.getMessage());
    }

    /**
     * 不支持的HttpMethod异常
     * @param e 异常信息
     * @return 异常信息反馈 {@link HttpStatus#METHOD_NOT_ALLOWED}
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedErrorHandler(Exception e) {
        return Result.no(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * 其他异常
     * @param e 异常信息
     * @return 统一的500异常信息 {@link HttpStatus#INTERNAL_SERVER_ERROR}
     */
    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Result.no(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
