package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    public static final String[] ENDPOINT_WITH_AUTHENTICATION_NOT_REQUIRE = {
            "/user/login",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    public static final String[] ENDPOINT_GET_WITH_AUTHENTICATION_REQUIRE_ADMIN_AND_EMPLOYEE = {
            "/medicos",
            "/medicos/*",
            "/pacientes",
            "/pacientes/*",
            "/pacientes/desativado",
            "/pacientes/especifico",
            "/consultas/mes",
            "/consultas/medico/mes"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                // group
                                .requestMatchers(ENDPOINT_WITH_AUTHENTICATION_NOT_REQUIRE).permitAll()
                                .requestMatchers(HttpMethod.GET, ENDPOINT_GET_WITH_AUTHENTICATION_REQUIRE_ADMIN_AND_EMPLOYEE).hasAnyRole("EMPLOYEE", "ADMIN")

                                // register
                                .requestMatchers(HttpMethod.POST, "/user/register").hasRole("ADMIN")

                                // medicos
                                .requestMatchers(HttpMethod.POST, "/medicos").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/medicos").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/medicos/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/medicos/reativar").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/medicos/desativado").hasRole("ADMIN")

                                // pacientes
                                .requestMatchers(HttpMethod.POST, "/pacientes").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/pacientes").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/pacientes/*").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/pacientes/reativar").hasAnyRole("EMPLOYEE", "ADMIN")

                                // consultas
                                .requestMatchers(HttpMethod.POST, "/consultas").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/consultas").hasAnyRole("EMPLOYEE", "ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
