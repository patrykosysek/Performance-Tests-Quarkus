package pl.polsl.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.dtos.firma.FirmaCreateDto;
import pl.polsl.entities.base.UuidBaseEntity;
import pl.polsl.enums.Kraj;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Firma extends UuidBaseEntity {

    @Column(nullable = false)
    private String nazwa;

    @Column(nullable = false, length = 10000)
    private String opis;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Kraj kraj;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Set<Plik> pliki = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Set<Transakcja> transakcje = new HashSet<>();


    public void dodajTransakcje(Transakcja transakcja) {
        this.transakcje.add(transakcja);
        transakcja.setFirma(this);
    }

    public void dodajPlik(Plik plik) {
        this.pliki.add(plik);
        plik.setFirma(this);
    }

    public Firma(FirmaCreateDto dto) {
        this.nazwa = dto.getNazwa();
        this.opis = dto.getOpis();
        this.kraj = dto.getKraj();
    }

}
