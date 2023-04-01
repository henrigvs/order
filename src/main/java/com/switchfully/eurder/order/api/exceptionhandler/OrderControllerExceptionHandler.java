package com.switchfully.eurder.order.api.exceptionhandler;

import com.switchfully.eurder.order.domain.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(OrderControllerExceptionHandler.class);

    /**
     * @requires    any runtime exception
     * @return      A Response Entity containing :
     *                      the Http status : BAD_REQUEST 400
     *                      errorResponse(e.getMessage(), 400, BAD_REQUEST)
     */
    private ResponseEntity<OrderErrorResponse> badRequestHandler(RuntimeException e){
        logger.error("Exception raised: ",  e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        OrderErrorResponse orderErrorResponse = new OrderErrorResponse(e.getMessage(), status.value(), status.getReasonPhrase());
        return new ResponseEntity<>(orderErrorResponse, status);
    }

    @ExceptionHandler(OrderIdDoesNotExistException.class)
    protected ResponseEntity<OrderErrorResponse> orderIdDoesNotExistException(RuntimeException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(IllegalDateOfOrder.class)
    protected ResponseEntity<OrderErrorResponse> illegalDateOfOrder(RuntimeException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(NegativeAmountOrdered.class)
    protected ResponseEntity<OrderErrorResponse> negativeAmountOrdered(RuntimeException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    protected ResponseEntity<OrderErrorResponse> orderAlreadyExistsException(RuntimeException e){
        return badRequestHandler(e);
    }

    @ExceptionHandler(NonExistingOrderException.class)
    protected ResponseEntity<OrderErrorResponse> nonExistingOrderException(RuntimeException e){
        return badRequestHandler(e);
    }

}
