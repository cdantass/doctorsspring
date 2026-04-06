package com.med.doctorss.controller;

import com.med.doctorss.entity.user.DadosAutenticacao;
import com.med.doctorss.entity.user.User;
import com.med.doctorss.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> effectLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var token = new UsernamePasswordAuthenticationToken(
                    dados.login(),
                    dados.senha()
            );

            var authentication = manager.authenticate(token);

            var user = (User) authentication.getPrincipal();

            var jwt = tokenService.gerarToken(user);

            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }
}