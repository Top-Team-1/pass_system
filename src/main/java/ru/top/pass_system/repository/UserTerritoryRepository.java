package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.pass_system.model.UserTerritory;
import ru.top.pass_system.model.UserTerritoryId;

import java.util.List;

public interface UserTerritoryRepository extends JpaRepository<UserTerritory, UserTerritoryId> {

    // Найти все назначения по пользователю
    List<UserTerritory> findByUser_Id(Long userId);

    // Найти все назначения по территории
    List<UserTerritory> findByTerritory_Id(Long territoryId);

    // Проверить, существует ли связь
    boolean existsByUser_IdAndTerritory_Id(Long userId, Long territoryId);
}