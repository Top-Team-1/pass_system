package ru.top.pass_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.pass_system.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByPhone(String phone);
}
