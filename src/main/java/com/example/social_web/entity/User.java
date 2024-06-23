package com.example.social_web.entity;
import com.example.social_web.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tbl_users"
)
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails , Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String telephone;
    String username;
    String password;
    String name;
    @Enumerated(EnumType.STRING)
    Role role;
    String imageUrl;
    boolean accountLocked;
    @OneToMany
    Set<User> followers = new HashSet<>();

    @OneToMany
    Set<User> followings = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getName() {
        return username;
    }
}
