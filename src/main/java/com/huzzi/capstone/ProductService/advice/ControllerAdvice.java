package com.huzzi.capstone.ProductService.advice;

import com.huzzi.capstone.ProductService.dto.ErrorDTO;
import com.huzzi.capstone.ProductService.errorhandler.ProductNotFoundException;
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
}
