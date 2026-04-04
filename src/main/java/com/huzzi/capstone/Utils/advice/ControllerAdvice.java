package com.huzzi.capstone.Utils.advice;

import com.huzzi.capstone.AuthService.errorhandler.AuthException;
import com.huzzi.capstone.ProductService.dto.ErrorDTO;
import com.huzzi.capstone.ProductService.errorhandler.ExternalApiException;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
import com.huzzi.capstone.UserService.errorhandler.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(404);
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(errorDTO, HttpStatusCode.valueOf(404));
        return responseEntity;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(404);
        return new ResponseEntity<>(errorDTO, HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorDTO> handleExternalApiException(ExternalApiException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(e.getStatusCode());
        return new ResponseEntity<>(errorDTO, HttpStatusCode.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDTO> handleAuthException(AuthException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(e.getStatusCode());
        return new ResponseEntity<>(errorDTO, HttpStatusCode.valueOf(e.getStatusCode()));
    }
}
