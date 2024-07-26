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
    @ManyToMany
    @JoinTable(
            name = "tbl_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    Set<FriendRequest> receivedFriendRequests = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    Set<FriendRequest> sentFriendRequests = new HashSet<>();



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
        return name;
    }
}
