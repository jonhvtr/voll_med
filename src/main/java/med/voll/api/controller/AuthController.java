package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.User;
import med.voll.api.domain.dto.DataAuth;
import med.voll.api.infra.security.TokenService;
import med.voll.api.infra.security.dto.DataTokenJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DataAuth dataAuth) {
        var authToken = new UsernamePasswordAuthenticationToken(dataAuth.login(), dataAuth.password());
        var authentication = authenticationManager.authenticate(authToken);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
    }
}
