package com.example.jwtservice.service;

import com.example.jwtservice.config.KeyPairConfig;
import com.example.jwtservice.entity.User;
import com.example.jwtservice.repositiry.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private KeyPairConfig keyPair;
    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Transactional
    public void register(User user){
        if(userRepository.findByLogin(user.getLogin()).isPresent()) throw new IllegalStateException("login taken");
        else if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new IllegalStateException("email taken");
        else {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(11)));
        userRepository.save(user);
        }
    }

    @Transactional
    public String login(User user) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        if(!userRepository.findByLogin(user.getLogin()).isPresent()) throw new IllegalStateException("login doesn't exist");
        else if (!BCrypt.checkpw(user.getPassword(),userRepository.findByLogin(user.getLogin()).orElseThrow(() -> new IllegalStateException(
                "mailStory with id " + user.getLogin() + " does not exists")).getPassword()
                )) throw new IllegalStateException("password os incorrect");
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("email", user.getEmail())
                .issuer(user.getLogin())
                .build();

        JWSSigner signer = new RSASSASigner(keyPair.getPrivate());

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claims);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }


}
