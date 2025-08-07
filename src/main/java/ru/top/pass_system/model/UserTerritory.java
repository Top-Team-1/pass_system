package ru.top.pass_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_territory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserTerritoryId.class) // Составной ключ
public class UserTerritory {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "territory_id")
    private Territory territory;

}
