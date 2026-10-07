package com.mentor.security.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Permission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* IF user 1 have ROLE_ADMIN - WRITE_USER READ_USER DELETE_USER UPDATE_USER */
    @Column(unique = true, nullable = false)
    private String name; //WRITE_ , READ_
}
