package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.entities.User;
import med.voll.api.domain.dto.DataAuth;
import med.voll.api.domain.dto.DataCreateUser;
import med.voll.api.infra.security.TokenService;
import med.voll.api.infra.security.dto.DataTokenJWT;
import med.voll.api.repository.UserRepository;
import med.voll.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid DataAuth dataAuth) {
        var authToken = new UsernamePasswordAuthenticationToken(dataAuth.login(), dataAuth.password());
        var authentication = authenticationManager.authenticate(authToken);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid DataCreateUser data) {
        userService.createEmployee(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
