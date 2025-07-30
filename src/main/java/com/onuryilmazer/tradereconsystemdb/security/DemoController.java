package com.onuryilmazer.tradereconsystemdb.security;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "You have ADMIN access.";
    }

    @GetMapping("/demo/guest")
    @PreAuthorize("hasRole('GUEST')")
    public String guestAccess() {
        return "You have GUEST access.";
    }
}
