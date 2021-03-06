package com.bestaone.aiwan.order.web.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/me")
    public Principal me(@AuthenticationPrincipal Principal principal) {
        return principal;
    }

    @GetMapping("/get")
    public HttpEntity get() {

        Map<String,Object> body = new HashMap<>();
        body.put("time", new Date());
        body.put("code", 10000);

        HttpHeaders header = new HttpHeaders();
        header.add("Auth","123456");

        HttpEntity e = new HttpEntity(body,header);
        return e;
    }

    @GetMapping("/user")
    public Object get(HttpServletRequest req) {
        SecurityContextImpl sci = (SecurityContextImpl) req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (sci != null) {
            Authentication authentication = sci.getAuthentication();
            if (authentication != null) {
                return authentication.getPrincipal();
            }
        }
        return "none";
    }

    @GetMapping("/error")
    public Principal error(@AuthenticationPrincipal Principal principal) {
        int i = 1/0;
        return principal;
    }

}