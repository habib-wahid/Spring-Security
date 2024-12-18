package com.example.spring_security.entity;

import com.example.spring_security.enums.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "roles")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Role {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
