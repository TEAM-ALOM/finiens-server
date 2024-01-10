package com.example.alom1.finiensserver.config.exception

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: Int
) {
    // Common
    INVALID_INPUT_VALUE(status=400, code = "C001", message = "Invalid Input Value"),
    METHOD_NOT_ALLOWED(status = 405, code = "C002", message = "Invalid Input Value"),
    ENTITY_NOT_FOUND(status = 400, code = "C003", message = "Entity Not Found"),
    INTERNAL_SERVER_ERROR(status = 500, code = "C004", message = "Server Error"),
    INVALID_TYPE_VALUE(status = 400, code = "C005", message = "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(status = 403, code = "C006", message = "Access is Denied"),

    // Member
    EMAIL_DUPLICATION(status = 400, code = "M001", message = "Email is Duplication"),
    LOGIN_INPUT_INVALID(status = 400, code = "M002", message = "Login input is invalid"),

    // Verification
    VERIFICATION_EXPIRED(status = 400, code = "V001", message = "Verification Code is Expired")
}