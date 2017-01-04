package com.template.core.servise;

import com.template.core.model.WebException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl {

    public void notNull(Object object, HttpStatus status, String errorMsg) throws WebException {
        if(object == null) throw new WebException(status, errorMsg);
    }

    public void notEmpty(String object, HttpStatus status, String errorMsg) throws WebException {
        if(object.isEmpty()) throw new WebException(status, errorMsg);
    }

    public void notNullAndNotEmpty(String object, HttpStatus status, String errorMsg) throws WebException {
        notNull(object, status, errorMsg);
        notEmpty(object, status, errorMsg);
    }
}