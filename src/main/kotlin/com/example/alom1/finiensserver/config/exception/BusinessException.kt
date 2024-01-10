package com.example.alom1.finiensserver.config.exception

import com.example.alom1.finiensserver.config.exception.ErrorCode

open class BusinessException(
    val errorCode: ErrorCode
): RuntimeException() {
}