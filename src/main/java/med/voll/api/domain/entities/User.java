package med.voll.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DataCreateUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "usuarios")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String login;
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;

    public User(DataCreateUser data) {
        this.login = data.login();
        this.senha = data.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name())).toList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
