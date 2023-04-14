package pl.polsl.dtos.firma;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.dtos.transakcja.TransakcjaDto;
import pl.polsl.entities.Firma;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SzczegolyFirmaDto extends FirmaDto {
    private Set<TransakcjaDto> transakcje;

    public SzczegolyFirmaDto(Firma firma, Set<TransakcjaDto> transakcje) {
        super(firma);
        this.transakcje = transakcje;
    }

}