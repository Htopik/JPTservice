package com.example.jwtservice.controller;
import com.example.jwtservice.config.KeyPairConfig;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PublicKeyController {

    @Autowired
    private JWKSet jwkSet;
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getPublicKey(){
        return this.jwkSet.toJSONObject();
    }
}
