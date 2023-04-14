package pl.polsl.dtos.transakcja;

import lombok.Data;
import pl.polsl.enums.Waluta;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransakcjaCreateDto {

    @NotNull
    private String nazwa;

    @NotNull
    private LocalDate dataTransakcji;

    @NotNull
    private BigDecimal kwotaTransakcji;

    @NotNull
    private Waluta waluta;
}