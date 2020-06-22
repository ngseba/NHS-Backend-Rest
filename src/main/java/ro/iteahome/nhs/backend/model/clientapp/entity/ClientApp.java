package ro.iteahome.nhs.backend.model.clientapp.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "client_apps")
public class ClientApp implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "CLIENT APP NAME CANNOT BE EMPTY.")
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @NotNull(message = "PASSWORD CANNOT BE EMPTY.")
    @Pattern(regexp = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,32})", message = "INVALID PASSWORD")
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(32)")
    private String password;

    @NotNull(message = "STATUS CANNOT BE EMPTY.")
    @Column(name = "status", nullable = false, columnDefinition = "INT")
    private byte status;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "client_apps_roles",
            joinColumns = @JoinColumn(name = "client_app_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role"))
    private Set<Role> roles;
    // TODO: This join table has no foreign keys. Only existing ClientApp and Role IDs should be accepted as values. Fix it.

// METHODS: ------------------------------------------------------------------------------------------------------------

    public ClientApp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // "getPassword()" IS PART OF THE "UserDetails" OVERRIDDEN METHODS.

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()))
                .collect(Collectors.toList());
    }

// OVERRIDDEN METHODS FROM "UserDetails" INTERFACE: --------------------------------------------------------------------

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != 3;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != 3;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != 4;
    }
}
