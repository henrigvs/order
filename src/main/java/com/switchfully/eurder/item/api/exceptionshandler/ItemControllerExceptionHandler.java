package com.switchfully.eurder.item.api.exceptionshandler;


import com.switchfully.eurder.item.domain.exceptions.*;
import com.switchfully.eurder.user.api.exceptionhandler.UserErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ItemControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ItemControllerExceptionHandler.class);

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : BAD_REQUEST 400
     *                      errorResponse(e.getMessage(), 400, BAD_REQUEST)
     */
    private ResponseEntity<ItemErrorResponse> badRequestHandler(RuntimeException e){
        logger.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ItemErrorResponse itemErrorResponse = new ItemErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(itemErrorResponse, status);
    }

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : FORBIDDEN 403
     *                      errorResponse(e.getMessage(), 403, FORBIDDEN)
     */
    private ResponseEntity<ItemErrorResponse> forbiddenRequestHandler(RuntimeException e){
        logger.error("Exception raised: ", e);
        HttpStatus status = HttpStatus.FORBIDDEN;
        ItemErrorResponse itemErrorResponse = new ItemErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(itemErrorResponse, status);
    }

    @ExceptionHandler(InvalidDescriptionFormatException.class)
    protected ResponseEntity<ItemErrorResponse> invalidDescriptionFormatException(InvalidDescriptionFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    protected ResponseEntity<ItemErrorResponse> invalidNameFormatException(InvalidNameFormatException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    protected ResponseEntity<ItemErrorResponse> itemAlreadyExistException(ItemAlreadyExistException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(ItemIdDoesNotExistException.class)
    protected ResponseEntity<ItemErrorResponse> itemIdDoesNotExistException(ItemIdDoesNotExistException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(NegativeAmountException.class)
    protected ResponseEntity<ItemErrorResponse> negativeAmountException(NegativeAmountException e){
        return forbiddenRequestHandler(e);
    }

    @ExceptionHandler(NegativePriceException.class)
    protected ResponseEntity<ItemErrorResponse> negativePriceException(NegativePriceException e){
        return forbiddenRequestHandler(e);
    }
}
