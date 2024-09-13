package com.phoenix.logistics.storage.db.core.product.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;
import com.phoenix.logistics.storage.db.core.product.entity.QProductEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaProductRepositoryImpl implements JpaProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QProductEntity product = QProductEntity.productEntity;

    @Override
    public Page<ProductEntity> searchProducts(String searchQuery, Pageable pageable) {
        List<ProductEntity> contents = queryFactory.select(product)
            .from(product)
            .where(searchQueryEq(searchQuery))
            .where(product.isDelete.isFalse())
            .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> count = queryFactory.select(product.count()).from(product).where(searchQueryEq(searchQuery));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanExpression searchQueryEq(String searchQuery) {
        return searchQuery.isEmpty() ? null : product.name.contains(searchQuery);
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            Path<Object> target = Expressions.path(Object.class, QProductEntity.productEntity, property);
            orderSpecifiers.add(new OrderSpecifier(direction, target));
        });
        return orderSpecifiers;
    }

}
