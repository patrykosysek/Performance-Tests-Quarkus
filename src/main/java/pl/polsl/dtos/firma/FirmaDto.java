package pl.polsl.dtos.firma;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.entities.Firma;
import pl.polsl.enums.Kraj;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FirmaDto {
    private UUID id;
    private String nazwa;
    private String opis;
    private Kraj kraj;

    public FirmaDto(Firma firma) {
        this.id = firma.getId();
        this.nazwa = firma.getNazwa();
        this.opis = firma.getOpis();
        this.kraj = firma.getKraj();
    }

}