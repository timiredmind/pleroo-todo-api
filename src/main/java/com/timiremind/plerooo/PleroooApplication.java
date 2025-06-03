package com.timiremind.plerooo;

import com.timiremind.plerooo.core.response.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PleroooApplication {

    public static void main(String[] args) {
        SpringApplication.run(PleroooApplication.class, args);
    }

    @RequestMapping(
            value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = {RequestMethod.GET})
    public ResponseEntity<Response<String>> welcome() {
        return ResponseEntity.ok(new Response<>(true, "Welcome to Pleroo"));
    }
}
