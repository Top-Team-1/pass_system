package ru.top.pass_system.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.pass_system.dto.userDTO.UserFilterDTO;
import ru.top.pass_system.enums.UserRole;
import ru.top.pass_system.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class UserSpecification {
    // Поиск по имени
    private static Specification<User> hasFirstNameLike(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    // Поиск по фамилии
    private static Specification<User> hasLastNameLike(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    // Фильтр по номеру телефона
    private static Specification<User> hasPhone(String phone) {
        return (root, query, cb) -> phone == null ? null :
                cb.equal(root.get("phone"), phone);
    }

    // Фильтр по дате рождения (пользователи, родившиеся после указанной даты)
    private static Specification<User> bornAfter(LocalDate date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("dateOfBirth"), date);
    }

    // Фильтр по роли
    private static Specification<User> hasRole(UserRole role) {
        return (root, query, cb) -> role == null ? null :
                cb.equal(root.get("role"), role);
    }

    // Фильтр по дате создания
    private static Specification<User> addedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("addedAt"), date);
    }

    // Фильтр по дате обновления
    private static Specification<User> updatedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
    }

    // Объединение спецификаций
    @SafeVarargs
    private static Specification<User> combine(Specification<User>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    // Метод создания спецификации
    public static Specification<User> createSpecification(UserFilterDTO userFilterDTO){

        return UserSpecification.combine(
                UserSpecification.hasFirstNameLike(userFilterDTO.getFirstName()),
                UserSpecification.hasLastNameLike(userFilterDTO.getLastName()),
                UserSpecification.hasPhone(userFilterDTO.getPhone()),
                UserSpecification.bornAfter(userFilterDTO.getDateOfBirth()),
                UserSpecification.hasRole(userFilterDTO.getRole()),
                UserSpecification.addedAfter(userFilterDTO.getAddedAfter()),
                UserSpecification.updatedAfter(userFilterDTO.getUpdatedAfter())
        );
    }
}
