package pl.polsl.dtos.transakcja;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class WieleTransakcjiDto {
    private Set<TransakcjaDto> transakcje;
}