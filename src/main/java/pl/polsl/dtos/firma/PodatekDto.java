package pl.polsl.dtos.firma;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class PodatekDto {
    private BigDecimal podatek;
}
