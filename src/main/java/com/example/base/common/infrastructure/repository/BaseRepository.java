package com.example.base.common.infrastructure.repository;

import com.example.base.web.dto.PageCreate;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.util.ProxyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class BaseRepository<E, I> {
    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory queryFactory;
    private JpaEntityInformation<E, I> entityInformation;

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return queryFactory.select(expr);
    }

    protected <T> JPAQuery<T> select(Class<T> clazz, Expression<?>... exprs) {
        return queryFactory.select(Projections.fields(clazz, exprs));
    }

    protected JPAQuery<E> selectFrom(EntityPath<E> from) {
        return queryFactory.selectFrom(from);
    }

    public Optional<E> findById(I i) {
        Assert.notNull(i, "The given id must not be null!");
        final E entity = em.find(this.entityInformation.getJavaType(), i);
        return Optional.ofNullable(entity);
    }

    @Transactional
    public E save(E entity) {
        if (Boolean.TRUE.equals(isNewEntity(entity))) {
            em.persist(entity);
            return entity;
        }

        return em.merge(entity);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void delete(E entity) {
        JpaEntityInformation<E, I> entityInfo = this.getJpaEntityInformation(entity.getClass());
        if (Boolean.TRUE.equals(isNewEntity(entity))) {
            return;
        }

        //동시성 문제 발생 대비
        E existing = (E) em.find(ProxyUtils.getUserClass(entity), entityInfo.getId(entity));
        if (existing == null) {
            return;
        }

        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @SuppressWarnings("unchecked")
    private JpaEntityInformation<E, I> getJpaEntityInformation(Class<?> clazz) {
        if (this.entityInformation == null) {
            this.entityInformation =
                    (JpaEntityInformation<E, I>) JpaEntityInformationSupport.getEntityInformation(clazz, em);
        }

        return this.entityInformation;
    }

    private Boolean isNewEntity(E entity) {
        return this.getJpaEntityInformation(entity.getClass()).isNew(entity);
    }

    protected <T> List<T> getPaginationContent(JPAQuery<T> contentQuery, PageCreate pageCreate) {
        Pageable pageable = PageRequest.of(pageCreate.getPage(), pageCreate.getSize());

        return this.pagingFetch(contentQuery, pageable);
    }

    protected Long getTotalCount(JPAQuery<?> countQuery) {
        return countQuery.fetchCount();
    }

    protected Object getTotalCountByQuery(JPAQuery<?> countQuery) {
        return countQuery.fetchOne();
    }

    private <T> List<T> pagingFetch(JPAQuery<T> query, Pageable pageable) {
        if (pageable.isUnpaged()) {
            return query.fetch();
        }

        return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
    }
}
