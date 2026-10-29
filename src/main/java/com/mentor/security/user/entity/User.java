package com.mentor.security.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //security
    @Column(name = "is_account_non_expired", nullable = false)
    private Boolean isAccountNonExpired = true; // ✅ ត្រូវជា true សម្រាប់ Account ថ្មី

    @Column(name = "is_credentials_non_expired", nullable = false)
    private Boolean isCredentialsNonExpired = true; // ✅ ត្រូវជា true

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false; // ✅ false (រង់ចាំការ Verify Email)

    @Column(name = "is_account_non_locked", nullable = false)
    private Boolean isAccountNonLocked = true; // ✅ ត្រូវជា true (មិនទាន់ជាប់សោ)

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles; // List<> , Set<> ,Map<key , value>
}
