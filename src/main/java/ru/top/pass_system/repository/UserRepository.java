package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.top.pass_system.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userTerritories WHERE u.id = :id")
    Optional<User> getUserWithZone(@Param("id") Long id);
}
