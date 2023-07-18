package com.fw.api.system.exception;

import com.fw.core.code.ResponseCode;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Common Exception Handler
 * @author sjpaik
 * @since 22.08.21
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final HttpServletRequest request;

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        request.setAttribute("error", e);
        return ResponseEntity.status(ResponseCode.INVALID_PARAMETER.getHttpStatus().value())
                .body(ResponseVO.builder(ResponseCode.INVALID_PARAMETER, new String[]{e.getBindingResult().getAllErrors().get(0).getDefaultMessage()}).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiBizException.class)
    protected ResponseEntity<ResponseVO> handleCustomException(ApiBizException e){
        request.setAttribute("error", e);
        return ResponseEntity.status(e.getResponseCode().getHttpStatus().value())
                .body(ResponseVO.builder(e.getResponseCode()).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ResponseVO> handleNoHandlerFoundException(Exception e){
        request.setAttribute("error", e);
        return ResponseEntity.status(ResponseCode.PAGE_NOT_FOUND.getHttpStatus().value())
                .body(ResponseVO.builder(ResponseCode.PAGE_NOT_FOUND).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseVO> handleHttpRequestMethodNotSupportedException(Exception e){
        request.setAttribute("error", e);
        return ResponseEntity.status(ResponseCode.METHOD_NOT_ALLOWED.getHttpStatus().value())
                .body(ResponseVO.builder(ResponseCode.METHOD_NOT_ALLOWED).build());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseVO> handleException(Exception e){
        request.setAttribute("error", e);
        return ResponseEntity.status(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .body(ResponseVO.builder(ResponseCode.INTERNAL_SERVER_ERROR).build());
    }

}
