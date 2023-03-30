package com.switchfully.eurder.user.api.exceptionhandler;

import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import com.switchfully.eurder.user.service.exceptions.InsufficientAccessRightException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UserControllerExceptionHandler.class);

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : BAD_REQUEST 400
     *                      errorResponse(e.getMessage(), 400, BAD_REQUEST)
     */
    private ResponseEntity<UserErrorResponse> badRequestHandler(RuntimeException e){
        logger.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(userErrorResponse, status);
    }

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : NOT_FOUND 404
     *                      errorResponse(e.getMessage(), 404, NOT_FOUND)
     */
    private ResponseEntity<UserErrorResponse> notFoundRequestHandler(RuntimeException e){
        logger.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.NOT_FOUND;
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(userErrorResponse, status);
    }

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : FORBIDDEN 403
     *                      errorResponse(e.getMessage(), 403, FORBIDDEN)
     */
    private ResponseEntity<UserErrorResponse> forbiddenRequestHandler(RuntimeException e){
        logger.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.FORBIDDEN;
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(userErrorResponse, status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<UserErrorResponse> emailAlreadyExistsException(EmailAlreadyExistsException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(UserIdAlreadyExistsException.class)
    protected ResponseEntity<UserErrorResponse> userIdAlreadyExistsException(UserIdAlreadyExistsException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidEmailFormatException(InvalidEmailFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidFirstNameFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidFirstNameFormatException(InvalidFirstNameFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidLastNameFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidLastNameFormatException(InvalidLastNameFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidPasswordFormatException(InvalidPasswordFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler
    protected ResponseEntity<UserErrorResponse> invalidUserIdFormatException(InvalidUserIdFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(NoEmailPasswordMatchException.class)
    protected ResponseEntity<UserErrorResponse> noEmailPasswordMatchException(NoEmailPasswordMatchException e){
        return notFoundRequestHandler(e);
    }
    
    @ExceptionHandler(NonExistingEmailException.class)
    protected ResponseEntity<UserErrorResponse> nonExistingEmailException(NonExistingEmailException e){
        return notFoundRequestHandler(e);
    }
    
    @ExceptionHandler(NonExistingUserIdException.class)
    protected ResponseEntity<UserErrorResponse> nonExistingUserIdException(NonExistingUserIdException e){
        return notFoundRequestHandler(e);
    }

    @ExceptionHandler(InvalidAddressFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidAddressFormatException(InvalidAddressFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidPhoneNumberFormatException.class)
    protected ResponseEntity<UserErrorResponse> invalidPhoneNumberFormatException(InvalidPhoneNumberFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InsufficientAccessRightException.class)
    protected ResponseEntity<UserErrorResponse> insufficientAccessRightException(InsufficientAccessRightException e){
        return forbiddenRequestHandler(e);
    }
}
