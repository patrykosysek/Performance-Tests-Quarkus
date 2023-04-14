package pl.polsl.dtos.firma;

import lombok.Data;
import pl.polsl.enums.Kraj;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FirmaCreateDto {

    @NotNull
    @Size(max = 255)
    private String nazwa;

    @Size(max = 10000)
    private String opis;

    @NotNull
    private Kraj kraj;

}