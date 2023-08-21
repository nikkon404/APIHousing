package com.conestoga.APIHousing.controller;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomErrorController implements ErrorController {
  Logger logger = Logger.getLogger(CustomErrorController.class.getName());
  private final ErrorAttributes errorAttributes;

  public CustomErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @RequestMapping("/error")
  public ResponseEntity<String> handleError(WebRequest request) {
    // Get the error attributes from the request
    Map<String, Object> errorAtributesMap =
        this.errorAttributes.getErrorAttributes(
            request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));

    logger.info("Error attributes " + errorAtributesMap.values());
    for (Map.Entry<String, Object> entry : errorAtributesMap.entrySet()) {
      logger.info(entry.getKey() + " : " + entry.getValue());
    }

    return new ResponseEntity<>(
        errorAtributesMap.toString(),
        errorAtributesMap.get("status").toString().equals("404")
            ? HttpStatus.NOT_FOUND
            : HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
