package med.voll.api.service;

import med.voll.api.domain.dto.DataAuth;
import med.voll.api.domain.dto.DataCreateUser;
import med.voll.api.domain.entities.Role;
import med.voll.api.domain.entities.User;
import med.voll.api.domain.enums.RoleName;
import med.voll.api.infra.security.SecurityConfigurations;
import med.voll.api.infra.security.TokenService;
import med.voll.api.infra.security.dto.DataTokenJWT;
import med.voll.api.repository.RoleRepository;
import med.voll.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityConfigurations securityConfigurations;

    public DataTokenJWT authenticateUser(DataAuth dataAuth) {
        var authToken = new UsernamePasswordAuthenticationToken(dataAuth.login(), dataAuth.password());
        var authentication = authenticationManager.authenticate(authToken);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return new DataTokenJWT(tokenJWT);
    }

    public void createEmployee(DataCreateUser data) {
        var existsLogin = userRepository.findByLogin(data.login());

        if (existsLogin.isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        Role employee = roleRepository.findByRole(RoleName.EMPLOYEE).orElseThrow(() -> new RuntimeException("Role EMPLOYEE não encontrado."));

        User newUser = User.builder()
                .login(data.login())
                .senha(securityConfigurations.passwordEncoder().encode(data.senha()))
                .role(List.of(employee))
                .build();

        userRepository.save(newUser);
    }
}
