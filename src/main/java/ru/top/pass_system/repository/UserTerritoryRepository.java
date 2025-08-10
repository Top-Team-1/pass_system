package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.top.pass_system.model.UserTerritory;
import ru.top.pass_system.model.UserTerritoryId;

import java.util.List;

public interface UserTerritoryRepository extends JpaRepository<UserTerritory, UserTerritoryId> {

    // Найти все назначения по пользователю
//    List<UserTerritory> findByUser_Id(Long userId);

    // Найти все назначения по территории
//    List<UserTerritory> findByTerritory_Id(Long territoryId);

    // Проверить, существует ли связь
    @Query(value = "SELECT EXISTS (SELECT 1 FROM user_territory WHERE user_id = :userId AND territory_id = :territoryId)", nativeQuery = true)
    boolean existsByUserIdAndTerritoryId(@Param("userId") Long userId, @Param("territoryId") Long territoryId);
}