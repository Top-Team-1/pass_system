package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.top.pass_system.model.Territory;

public interface TerritoryRepository extends JpaRepository<Territory, Long>, JpaSpecificationExecutor<Territory> {
    boolean existsByAddress(String address);
}
