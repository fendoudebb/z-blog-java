package com.msj.blog.exception;

import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //权限不足异常
    /*@ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handlerAccessDeniedException() {
        return new ModelAndView("error/403");
    }*/

    //路径不存在异常
    /*@ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handlerNoHandlerFoundException() {
        return new ModelAndView("error/404");
    }*/

  /*  @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handlerTemplateInputException(TemplateInputException e) {
        log.error("ip: {}, {} template not found", IpUtil.getIpAddress() ,e.getTemplateName());
        return new ModelAndView("error/404");
    }*/

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception e) {
        log.error("ExceptionHandler -> {}", e.getMessage());
        Response response = new Response();
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            response.setMsg(defaultMessage);
        } else if (e instanceof MissingServletRequestParameterException) {
            response.setMsg(MsgTable.MISSING_PARAMETERS);
        } else if (e instanceof TypeMismatchException) {
            response.setMsg(MsgTable.MISMATCH_TYPE);
        } else if (e instanceof HttpMessageNotReadableException){
            response.setMsg(MsgTable.MISSING_REQUEST_PARAM);
        } else if (e instanceof HttpRequestMethodNotSupportedException){
            response.setMsg(MsgTable.REQUEST_METHOD_NOT_SUPPORT);
        } else {
            response.setMsg(MsgTable.INTERNAL_ERROR);
        }
        return response.fail();
    }

}