package com.example.alom1.finiensserver.config.exception

import com.example.alom1.finiensserver.presentation.core.ErrorResponseBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.BindException

@ControllerAdvice
class GlobalExceptionHandler {
    // @RequestBody, @RequestPart 어노테이션에서 발생
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            errorCode = ErrorCode.METHOD_NOT_ALLOWED
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.BAD_REQUEST)
    }

    // @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            errorCode = ErrorCode.INVALID_INPUT_VALUE
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.BAD_REQUEST)
    }

    // enum type 일치 하지 않아 binding 못할 경우 발생
//    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
//    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponseBody> {
//        val response: ErrorResponseBody = ErrorResponseBody(
//            errorCode = ErrorCode.INVALID_TYPE_VALUE
//        );
//        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.BAD_REQUEST)
//    }

    // 지원하지 않은 HTTP method 호출 할 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            errorCode = ErrorCode.METHOD_NOT_ALLOWED
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    // authentication 객체가 필요한 권한을 보유하지 않는 경우
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            errorCode = ErrorCode.HANDLE_ACCESS_DENIED
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.status))
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            errorCode = e.errorCode
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.valueOf(e.errorCode.status))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponseBody> {
        val response: ErrorResponseBody = ErrorResponseBody(
            ErrorCode.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity<ErrorResponseBody>(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }


}