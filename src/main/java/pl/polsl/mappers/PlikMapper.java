package pl.polsl.mappers;

import pl.polsl.dtos.plik.PlikDto;
import pl.polsl.dtos.plik.PlikListDto;
import pl.polsl.entities.Plik;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PlikMapper {

    public Plik doPlik(String sciezka, String typPliku) {
        return new Plik(sciezka, typPliku);
    }

    public PlikDto doPlikDto(Plik plik) {
        return new PlikDto(plik);
    }

    public PlikListDto doPlikListDto(List<byte[]> faktury) {
        return new PlikListDto(faktury);
    }
}
