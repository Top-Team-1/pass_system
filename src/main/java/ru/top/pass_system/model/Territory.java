package ru.top.pass_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "territories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @CreationTimestamp
    private LocalDateTime addedAt;
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "territories")
    private List<User> users;
}
