package ro.iteahome.nhs.backend.model.clientapp.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "status", nullable = false, columnDefinition = "INT")
    private int status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "client_apps_roles",
            joinColumns = @JoinColumn(name = "client_app_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

// OVERRIDDEN METHODS FROM "UserDetails" INTERFACE: --------------------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
                .collect(Collectors.toList());
    }

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
