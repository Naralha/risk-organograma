package br.com.obelisco.risk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void create() {
        if (id == null) id = UUID.randomUUID();
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void update() { updatedAt = LocalDateTime.now(); }

    @Override
    public boolean equals(Object other) {
        return this == other || other != null && getClass() == other.getClass()
            && id != null && id.equals(((BaseEntity) other).id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
