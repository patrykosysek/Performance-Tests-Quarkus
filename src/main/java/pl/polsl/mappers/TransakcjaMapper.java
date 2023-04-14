package pl.polsl.mappers;

import pl.polsl.dtos.transakcja.TransakcjaCreateDto;
import pl.polsl.dtos.transakcja.TransakcjaDto;
import pl.polsl.dtos.transakcja.WieleTransakcjiDto;
import pl.polsl.entities.Transakcja;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;


@ApplicationScoped
public class TransakcjaMapper {

    public Transakcja doTransakcji(TransakcjaCreateDto dto) {
        return new Transakcja(dto);
    }

    public TransakcjaDto doTransakcjiDto(Transakcja transakcja) {
        return new TransakcjaDto(transakcja);
    }

    public WieleTransakcjiDto doWieleTransakcjiDto(Set<Transakcja> transakcje) {
        Set<TransakcjaDto> transakcjaDtoList = doListyTransakcjiDto(transakcje);
        return new WieleTransakcjiDto(transakcjaDtoList);
    }

    public Set<TransakcjaDto> doListyTransakcjiDto(Set<Transakcja> transakcje) {
        Set<TransakcjaDto> transakcjaDtoSet = new HashSet<>();

        for (Transakcja transakcja : transakcje) {
            transakcjaDtoSet.add(doTransakcjiDto(transakcja));
        }
        return transakcjaDtoSet;
    }
}
