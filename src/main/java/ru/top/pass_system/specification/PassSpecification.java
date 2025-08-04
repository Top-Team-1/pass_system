package ru.top.pass_system.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.pass_system.dto.passDTO.PassFilterDTO;
import ru.top.pass_system.enums.PassStatus;
import ru.top.pass_system.enums.PassType;
import ru.top.pass_system.model.Pass;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import java.util.Objects;

public class PassSpecification {

    // Спецификация для выбора пропусков по ID пользователя
    private static Specification<Pass> byUserId(Long userId) {
        return (root, query, builder) ->
                userId != null ?
                        builder.equal(root.join("user").get("id"), userId) : null;
    }

    // Спецификация для пропуска по территории
    private static Specification<Pass> byTerritoryId(Long territoryId) {
        return (root, query, builder) ->
                territoryId != null ?
                        builder.equal(root.join("territory").get("id"), territoryId) : null;
    }

    // Поиск по статусу пропуска
    private static Specification<Pass> withStatus(PassStatus status) {
        return (root, query, builder) ->
                status != null ?
                        builder.equal(root.get("status"), status) : null;
    }

    // Поиск по типу пропуска
    private static Specification<Pass> withType(PassType type) {
        return (root, query, builder) ->
                type != null ?
                        builder.equal(root.get("type"), type) : null;
    }

    // Фильтрация по началу срока действия пропуска
    private static Specification<Pass> startsBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, builder) -> {
            if (from == null && to == null) return null;
            Predicate predicate = null;
            if (from != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("startDate"), from);
            }
            if (to != null) {
                Predicate p = builder.lessThanOrEqualTo(root.get("startDate"), to);
                predicate = predicate == null ? p : builder.and(predicate, p);
            }
            return predicate;
        };
    }

    // Фильтрация по концу срока действия пропуска
    private static Specification<Pass> endsBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, builder) -> {
            if (from == null && to == null) return null;
            Predicate predicate = null;
            if (from != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("endDate"), from);
            }
            if (to != null) {
                Predicate p = builder.lessThanOrEqualTo(root.get("endDate"), to);
                predicate = predicate == null ? p : builder.and(predicate, p);
            }
            return predicate;
        };
    }

    // Добавление новых пропусков после определённой даты
    private static Specification<Pass> addedAfter(LocalDateTime date) {
        return (root, query, builder) -> date != null ?
                builder.greaterThanOrEqualTo(root.get("addedAt"), date) : null;
    }

    // Обновления после определённого момента времени
    private static Specification<Pass> updatedAfter(LocalDateTime date) {
        return (root, query, builder) -> date != null ?
                builder.greaterThanOrEqualTo(root.get("updatedAt"), date) : null;
    }

    // Объединяем спецификации в одну общую
    private static Specification<Pass> combine(List<Specification<Pass>> specifications) {
        return specifications.stream()
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    // Основной метод создания спецификации по переданным критериям
    public static Specification<Pass> createSpecification(PassFilterDTO passFilterDTO) {
        List<Specification<Pass>> specs = new ArrayList<>();

        specs.add(byUserId(passFilterDTO.getUserId()));
        specs.add(byTerritoryId(passFilterDTO.getTerritoryId()));
        specs.add(startsBetween(passFilterDTO.getStartDateFrom(), passFilterDTO.getStartDateTo()));
        specs.add(endsBetween(passFilterDTO.getEndDateFrom(), passFilterDTO.getEndDateTo()));
        specs.add(withStatus(passFilterDTO.getStatus()));
        specs.add(withType(passFilterDTO.getType()));
        specs.add(addedAfter(passFilterDTO.getAddedAfter()));
        specs.add(updatedAfter(passFilterDTO.getUpdatedAfter()));

        return combine(specs);
    }
}
