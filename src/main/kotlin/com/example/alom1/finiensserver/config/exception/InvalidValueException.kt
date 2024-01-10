import com.example.alom1.finiensserver.config.exception.BusinessException
import com.example.alom1.finiensserver.config.exception.ErrorCode

open class InvalidValueException() : BusinessException(
    errorCode = ErrorCode.INVALID_INPUT_VALUE
)