package pl.polsl.entities.base;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id", callSuper = false)
public abstract class UuidBaseEntity extends PanacheEntityBase {
    @Id
    @Column(updatable = false, nullable = false, unique = true)
    protected UUID id = UUID.randomUUID();
}
