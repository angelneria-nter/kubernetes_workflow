package com.example.angelneria_final_project.domain.entities;

import com.example.angelneria_final_project.domain.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_active = false WHERE id = ?")
@Where(clause = "is_active = true")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne()
    private Country country;
    @Enumerated(EnumType.STRING)
    private Roles rol= Roles.USER;

}
