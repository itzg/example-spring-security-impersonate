package me.itzg.example.customauth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Geoff Bourne
 * @since Mar 2017
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("me")
    public ResponseEntity<String> whoami(@AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
