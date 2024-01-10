package com.example.alom1.finiensserver.config.exception

class EntityNotFoundException(): BusinessException(
    errorCode = ErrorCode.ENTITY_NOT_FOUND
)