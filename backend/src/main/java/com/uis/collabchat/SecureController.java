package com.uis.collabchat;

import com.uis.collabchat.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping(path = "/secure", produces = "text/html")
    public ResponseEntity<String> secure(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Please login first");
        }
        String token = auth.substring(7);// Remove "Bearer " prefix???
        try {
            Jws<Claims> claims = JwtUtil.validateToken(token);
            String user = claims.getBody().getSubject();
            String body = "<html><body>Welcome <h1>" + user + "</h1><p>This is a protected page.</p></body></html>";
            return ResponseEntity.ok(body);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Please login first");
        }
    }
}