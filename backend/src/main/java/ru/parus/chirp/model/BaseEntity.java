package ru.parus.chirp.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * BaseEntity
 * <p>
 *     Базовый класс сущностей
 * </p>
 *
 * @author Grachev.D.G  (zhulvern-92@mail.ru)
 * @version 30.01.2026
 */
@MappedSuperclass
@FilterDef(name = "isDeletedIsFalseFilter",
        defaultCondition = "deleted<>true")
@Filter(name = "isDeletedIsFalseFilter")
public class BaseEntity {

    public BaseEntity() {
        this.isDeleted = Boolean.FALSE;
    }

    public BaseEntity(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    protected LocalDateTime updatedAt;

    @Column(name = "deleted")
    protected Boolean isDeleted;

    @Version
    protected int version;

}
