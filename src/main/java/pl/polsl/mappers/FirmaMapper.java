package pl.polsl.mappers;

import pl.polsl.dtos.firma.FirmaCreateDto;
import pl.polsl.dtos.firma.FirmaDto;
import pl.polsl.dtos.firma.FirmaUpdateDto;
import pl.polsl.dtos.firma.SzczegolyFirmaDto;
import pl.polsl.dtos.transakcja.TransakcjaDto;
import pl.polsl.entities.Firma;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class FirmaMapper {
    @Inject
    TransakcjaMapper transakcjaMapper;

    public Firma doFirmy(FirmaCreateDto dto) {
        return new Firma(dto);
    }

    public FirmaDto doFimraDto(Firma firma) {
        return new FirmaDto(firma);
    }

    public Firma aktualizujFirme(Firma firma, FirmaUpdateDto dto) {
        Optional.ofNullable(dto.getNazwa()).ifPresent(firma::setNazwa);
        Optional.ofNullable(dto.getOpis()).ifPresent(firma::setOpis);
        Optional.ofNullable(dto.getKraj()).ifPresent(firma::setKraj);

        return firma;
    }

    public SzczegolyFirmaDto toSzczegolyFirmaDto(Firma firma) {
        Set<TransakcjaDto> transakcje = transakcjaMapper.doListyTransakcjiDto(firma.getTransakcje());
        return new SzczegolyFirmaDto(firma, transakcje);
    }
}
