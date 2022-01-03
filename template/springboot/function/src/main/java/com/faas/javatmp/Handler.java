package com.faas.javatmp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Handler {
    @GetMapping("/**")
    public ResponseEntity<String> print(@RequestParam Map<String, String> reqParams) {
        if (!addSpanTag(reqParams)) {
            return ResponseEntity.badRequest().body("Request params parsed failed");
        }

        return ResponseEntity.ok("Hello World");
    }

    private boolean addSpanTag(Map<String, String> reqParams) {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(reqParams);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        ActiveSpan.tag("reqParams", json);
        return true;
    }
}
