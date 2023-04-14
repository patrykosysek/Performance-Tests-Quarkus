package pl.polsl.dtos.firma;

import lombok.Data;
import pl.polsl.enums.Kraj;
import javax.validation.constraints.Size;

@Data
public class FirmaUpdateDto {
    @Size(max = 255)
    private String nazwa;

    @Size(max = 10000)
    private String opis;

    private Kraj kraj;
}