package ru.top.pass_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "territory_id")
    private Territory territory;

    private boolean isActive;

}
