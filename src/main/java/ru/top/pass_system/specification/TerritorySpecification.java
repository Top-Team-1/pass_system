package ru.top.pass_system.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.pass_system.dto.territoryDTO.TerritoryFilterDTO;
import ru.top.pass_system.enums.TerritoryType;
import ru.top.pass_system.model.Territory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class TerritorySpecification {
    // Поиск по имени (частичное совпадение, без учёта регистра)
    private static Specification<Territory> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Поиск по адресу (частичное совпадение, без учёта регистра)
    private static Specification<Territory> hasAddressLike(String address) {
        return (root, query, cb) -> address == null ? null :
                cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    // Фильтр по типу территории
    private static Specification<Territory> hasType(TerritoryType type) {
        return (root, query, cb) -> type == null ? null :
                cb.equal(root.get("type"), type);
    }

    // Фильтр по дате добавления (после указанной даты-времени)
    private static Specification<Territory> addedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("addedAt"), date);
    }

    // Фильтр по дате обновления (после указанной даты-времени)
    private static Specification<Territory> updatedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
    }

    // Утилита для объединения спецификаций (логическое AND)
    @SafeVarargs
    private static Specification<Territory> combine(Specification<Territory>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    public static Specification<Territory> createSpecification(TerritoryFilterDTO territoryFilterDTO) {
        return TerritorySpecification.combine(
                TerritorySpecification.hasNameLike(territoryFilterDTO.getName()),
                TerritorySpecification.hasAddressLike(territoryFilterDTO.getAddress()),
                TerritorySpecification.hasType(territoryFilterDTO.getType()),
                TerritorySpecification.addedAfter(territoryFilterDTO.getAddedAt()),
                TerritorySpecification.updatedAfter(territoryFilterDTO.getUpdatedAt())
        );
    }
}
