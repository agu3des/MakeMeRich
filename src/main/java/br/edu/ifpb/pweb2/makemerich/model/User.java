package br.edu.ifpb.pweb2.makemerich.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private Boolean enabled;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    @ToString.Exclude
    List<Authority> authorities;

    public boolean isAdmin() {
        boolean isAdmin = this.getAuthorities()
                .stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        if (isAdmin) {
            if (!this.isAdmin()) {
                Authority.AuthorityId id = new Authority.AuthorityId(this.username, "ROLE_ADMIN");
                Authority a = new Authority();
                a.setId(id);
                a.setUsername(this);
                a.setAuthority("ROLE_ADMIN");
                this.getAuthorities().add(a);
            }
        } else {
            if (this.isAdmin()) {
                this.getAuthorities().removeIf(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
            }
        }
    }

    public List<Authority> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }
}