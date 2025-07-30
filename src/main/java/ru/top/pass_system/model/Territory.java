package ru.top.pass_system.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String description;

}
