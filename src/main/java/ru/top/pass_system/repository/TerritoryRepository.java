package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.pass_system.model.Territory;

public interface TerritoryRepository extends JpaRepository<Territory, Long> {
}
