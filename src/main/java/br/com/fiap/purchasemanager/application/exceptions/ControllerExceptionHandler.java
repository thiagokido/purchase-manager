package br.com.fiap.purchasemanager.application.exceptions;

import br.com.fiap.purchasemanager.domain.exceptions.NotFoundException;
import br.com.fiap.purchasemanager.domain.exceptions.OrderException;
import br.com.fiap.purchasemanager.domain.exceptions.PurchaseBoardException;
import br.com.fiap.purchasemanager.domain.exceptions.PurchaseRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private StandardError error = new StandardError();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(
            NotFoundException e,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(PurchaseRequestException.class)
    public ResponseEntity<StandardError> purchaseRequestException(
            PurchaseRequestException e,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(status.name());
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(PurchaseBoardException.class)
    public ResponseEntity<StandardError> purchaseBoardException(
            PurchaseBoardException e,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(status.name());
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<StandardError> orderException(
            OrderException e,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(status.name());
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }
}
