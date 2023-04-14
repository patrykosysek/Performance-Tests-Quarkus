package pl.polsl.dtos.transakcja;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class WieleTransakcjiCreateDto {
    @NotEmpty
    private List<TransakcjaCreateDto> transakcje;
}