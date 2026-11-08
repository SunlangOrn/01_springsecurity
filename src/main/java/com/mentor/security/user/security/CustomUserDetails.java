package com.mentor.security.user.security;

import com.mentor.security.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.user = user;
        this.authorities = user.getRoles().stream()          // runs while the session is open
                .<GrantedAuthority>map(r -> new SimpleGrantedAuthority(r.getName()))
                .toList();
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getEmail(); }
    @Override public boolean isAccountNonExpired() { return Boolean.TRUE.equals(user.getIsAccountNonExpired()); }
    @Override public boolean isAccountNonLocked() { return Boolean.TRUE.equals(user.getIsAccountNonLocked()); }
    @Override public boolean isCredentialsNonExpired() { return Boolean.TRUE.equals(user.getIsCredentialsNonExpired()); }
    @Override public boolean isEnabled() {
        return !Boolean.TRUE.equals(user.getIsDeleted()) && Boolean.TRUE.equals(user.getIsVerified());
    }
}