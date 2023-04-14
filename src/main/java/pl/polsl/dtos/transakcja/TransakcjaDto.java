package pl.polsl.dtos.transakcja;

import lombok.Data;
import pl.polsl.entities.Transakcja;
import pl.polsl.enums.Waluta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TransakcjaDto {
    private UUID id;
    private String nazwa;
    private LocalDate dataTransakcji;
    private BigDecimal kwotaTransakcji;
    private Waluta waluta;

    public TransakcjaDto(Transakcja transakcja) {
        this.id = transakcja.getId();
        this.nazwa = transakcja.getNazwa();
        this.dataTransakcji = transakcja.getDataTransakcji();
        this.kwotaTransakcji = transakcja.getKwotaTransakcji();
        this.waluta = transakcja.getWaluta();
    }

}