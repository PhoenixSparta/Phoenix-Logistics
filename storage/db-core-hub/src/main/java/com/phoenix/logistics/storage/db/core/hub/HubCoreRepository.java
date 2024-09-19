package com.phoenix.logistics.storage.db.core.hub;

import com.phoenix.logistics.core.hub.domain.Cursor;
import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubRepository;
import com.phoenix.logistics.core.hub.domain.HubResult;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import com.phoenix.logistics.core.hub.domain.SortDirection;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class HubCoreRepository implements HubRepository {

    private final HubJpaRepository hubJpaRepository;

    private final JPAQueryFactory jpaQueryFactory;

    public HubCoreRepository(HubJpaRepository hubJpaRepository, EntityManager entityManager) {
        this.hubJpaRepository = hubJpaRepository;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public HubWithUuid add(Hub hub) {
        return hubJpaRepository.save(HubEntity.of(hub)).toHubWithUuid();
    }

    @Override
    public HubResult read(UUID hubUuid) {
        return hubJpaRepository.findByUuid(hubUuid).orElseThrow().toHubResult();
    }

    @Override
    public List<HubResult> find(Cursor cursor) {
        QHubEntity qHubEntity = QHubEntity.hubEntity;
        BooleanExpression searchCondition = createSearchCondition(qHubEntity, cursor.query(), cursor.searchBy());
        BooleanExpression cursorCondition = createCursorCondition(qHubEntity, cursor.cursor(), cursor.sortKey(),
                cursor.sort());
        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(qHubEntity, cursor.sortKey(), cursor.sort());

        return jpaQueryFactory.selectFrom(qHubEntity)
            .where(searchCondition, cursorCondition)
            .orderBy(orderSpecifier)
            .limit(cursor.limit())
            .fetch()
            .stream()
            .map(HubEntity::toHubResult)
            .toList();
    }

    private BooleanExpression createSearchCondition(QHubEntity qHubEntity, String query, String searchBy) {
        if (query == null || query.isEmpty()) {
            return null;
        }

        if ("name".equalsIgnoreCase(searchBy)) {
            return qHubEntity.name.containsIgnoreCase(query);
        }
        else if ("city".equalsIgnoreCase(searchBy)) {
            return qHubEntity.city.containsIgnoreCase(query);
        }
        else if ("fullAddress".equalsIgnoreCase(searchBy)) {
            return qHubEntity.fullAddress.containsIgnoreCase(query);
        }
        else {
            return qHubEntity.name.containsIgnoreCase(query)
                .or(qHubEntity.city.containsIgnoreCase(query))
                .or(qHubEntity.fullAddress.containsIgnoreCase(query));
        }
    }

    private BooleanExpression createCursorCondition(QHubEntity qHubEntity, String cursor, String sortKey,
            SortDirection sort) {
        if (cursor == null || cursor.isEmpty()) {
            return null;
        }

        int sequence = Integer.parseInt(cursor);

        if (sort == SortDirection.ASC) {
            return qHubEntity.sequence.gt(sequence);
        }
        else {
            return qHubEntity.sequence.lt(sequence);
        }
    }

    private OrderSpecifier<?> createOrderSpecifier(QHubEntity qHubEntity, String sortKey, SortDirection sort) {
        Order order = sort == SortDirection.ASC ? Order.ASC : Order.DESC;

        if ("createdAt".equalsIgnoreCase(sortKey)) {
            return new OrderSpecifier<>(order, qHubEntity.createdAt);
        }
        else if ("updatedAt".equalsIgnoreCase(sortKey)) {
            return new OrderSpecifier<>(order, qHubEntity.updatedAt);
        }
        else if ("sequence".equalsIgnoreCase(sortKey)) {
            return new OrderSpecifier<>(order, qHubEntity.sequence);
        }
        else if ("name".equalsIgnoreCase(sortKey)) {
            return new OrderSpecifier<>(order, qHubEntity.name);
        }

        return new OrderSpecifier<>(order, qHubEntity.createdAt);
    }

}
