package ru.top.pass_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.top.pass_system.enums.PassStatus;
import ru.top.pass_system.enums.PassType;

import java.time.LocalDateTime;

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

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private PassStatus status;
    @Enumerated(EnumType.STRING)
    private PassType type;

    @CreationTimestamp
    private LocalDateTime addedAt;
    @CreationTimestamp
    private LocalDateTime updatedAt;

    private String firstName;
    private String lastName;

}
