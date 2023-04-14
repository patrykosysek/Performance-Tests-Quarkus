package pl.polsl.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.entities.base.UuidBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"firma"})
@AllArgsConstructor
@NoArgsConstructor
public class Plik extends UuidBaseEntity {

    @ManyToOne(optional = false)
    private Firma firma;

    @Column(nullable = false, length = 1000)
    private String sciezka;

    private String typPliku;

    public Plik(String sciezka, String typPliku) {
        this.sciezka = sciezka;
        this.typPliku = typPliku;
    }

    public static List<Plik> listAllByFirmaId(UUID firmaId) {
        return list("firma_id", firmaId);
    }

}
