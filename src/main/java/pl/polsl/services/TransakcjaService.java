package pl.polsl.services;

import lombok.RequiredArgsConstructor;
import pl.polsl.entities.Transakcja;

import javax.enterprise.context.ApplicationScoped;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class TransakcjaService {


//    public Transakcja getById(UUID id) {
//        return transakcjaRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("TRANSAKCJA NIEZNALEZIONA"));
//    }

}
