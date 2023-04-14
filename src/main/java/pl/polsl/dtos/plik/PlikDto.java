package pl.polsl.dtos.plik;

import lombok.Data;
import pl.polsl.entities.Plik;

import java.util.UUID;

@Data
public class PlikDto {
    private UUID id;
    private String sciezka;
    private String typPliku;

    public PlikDto(Plik plik) {
        this.id = plik.getId();
        this.sciezka = plik.getSciezka();
        this.typPliku = plik.getTypPliku();
    }

}