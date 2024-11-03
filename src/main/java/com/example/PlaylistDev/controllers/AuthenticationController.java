package com.example.PlaylistDev.controllers;

import com.example.PlaylistDev.domain.auth.AuthenticationDto;
import com.example.PlaylistDev.domain.auth.LoginResDto;
import com.example.PlaylistDev.domain.auth.RegisterDto;
import com.example.PlaylistDev.domain.user.UserModel;
import com.example.PlaylistDev.repository.UserRepository;
import com.example.PlaylistDev.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody @Validated AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return new ResponseEntity<>(new LoginResDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserModel> register(@RequestBody @Validated RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUserModel = new UserModel(data.login(), encryptedPassword, data.role());

        this.repository.save(newUserModel);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}