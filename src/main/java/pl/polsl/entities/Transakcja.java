package pl.polsl.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeExclude;
import pl.polsl.dtos.transakcja.TransakcjaCreateDto;
import pl.polsl.entities.base.UuidBaseEntity;
import pl.polsl.enums.Waluta;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "firma")
@AllArgsConstructor
@NoArgsConstructor
public class Transakcja extends UuidBaseEntity {

    @ManyToOne(optional = false)
    @HashCodeExclude
    private Firma firma;

    @Column(nullable = false)
    private String nazwa;

    @Column(nullable = false)
    private LocalDate dataTransakcji;

    @Column(nullable = false)
    private BigDecimal kwotaTransakcji;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Waluta waluta;

    public Transakcja(TransakcjaCreateDto dto) {
        this.nazwa = dto.getNazwa();
        this.dataTransakcji = dto.getDataTransakcji();
        this.kwotaTransakcji = dto.getKwotaTransakcji();
        this.waluta = dto.getWaluta();
    }

}