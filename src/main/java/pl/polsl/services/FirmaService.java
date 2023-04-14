package pl.polsl.services;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pl.polsl.ExceptionUtils;
import pl.polsl.PageImpl;
import pl.polsl.dtos.firma.*;
import pl.polsl.dtos.plik.PlikDto;
import pl.polsl.dtos.plik.PlikListDto;
import pl.polsl.dtos.transakcja.TransakcjaCreateDto;
import pl.polsl.dtos.transakcja.TransakcjaDto;
import pl.polsl.dtos.transakcja.WieleTransakcjiCreateDto;
import pl.polsl.dtos.transakcja.WieleTransakcjiDto;
import pl.polsl.entities.Firma;
import pl.polsl.entities.Plik;
import pl.polsl.entities.Transakcja;
import pl.polsl.mappers.FirmaMapper;
import pl.polsl.mappers.PlikMapper;
import pl.polsl.mappers.TransakcjaMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class FirmaService {
    @Inject
    FirmaMapper firmaMapper;

    @Inject
    TransakcjaMapper transakcjaMapper;

    @Inject
    PlikMapper plikMapper;

    @Inject
    ExceptionUtils exceptionUtils;


    public Firma getById(UUID id) {
        PanacheQuery<Firma> query = Firma.find("id", id);
        Firma firma = query.firstResult();

        if (firma == null) {
            throw exceptionUtils.noSuchElementException("FIRMA NIEZNALEZIONA");
        }

        return firma;
    }

    @Transactional
    public FirmaDto stworzFirme(FirmaCreateDto dto) {
        Firma firma = firmaMapper.doFirmy(dto);
        Firma.persist(firma);

        return firmaMapper.doFimraDto(firma);
    }

    public PageImpl<FirmaDto> pobierzFirmy(FilterDto dto) {
        PanacheQuery<Firma> query = Firma.findAll();
        query.page(Page.of(dto.getPage(), dto.getSize()));

        List<FirmaDto> dtoList = query.stream().map(firmaMapper::doFimraDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, query.page().index, query.page().size, query.count());
    }

    @Transactional
    public FirmaDto zaktualizujFirme(UUID id, FirmaUpdateDto dto) {
        Firma firma = Firma.findById(id);
        if (firma == null) {
            throw exceptionUtils.noSuchElementException("FIRMA NIEZNALEZIONA");
        }
        firmaMapper.aktualizujFirme(firma, dto);

        return firmaMapper.doFimraDto(firma);
    }

    @Transactional
    public void usunFirme(UUID id) {
        Firma firma = Firma.findById(id);
        if (firma == null) {
            throw exceptionUtils.noSuchElementException("FIRMA NIEZNALEZIONA");
        }
        Firma.deleteById(id);
    }

    @Transactional
    public TransakcjaDto dodajTransakcje(UUID firmaId, TransakcjaCreateDto dto) {
        Firma firma = Firma.findById(firmaId);
        if (firma == null) {
            throw exceptionUtils.noSuchElementException("FIRMA NIEZNALEZIONA");
        }
        Transakcja transakcja = transakcjaMapper.doTransakcji(dto);
        firma.dodajTransakcje(transakcja);
        Firma.persist(firma);

        return transakcjaMapper.doTransakcjiDto(transakcja);
    }

    @Transactional
    public WieleTransakcjiDto dodajWieleTransakcji(UUID firmaId, WieleTransakcjiCreateDto dto) {
        Firma firma = Firma.findById(firmaId);
        if (firma == null) {
            throw exceptionUtils.noSuchElementException("FIRMA NIEZNALEZIONA");
        }
        List<TransakcjaCreateDto> transakcjaCreateDtoList = dto.getTransakcje();
        Set<Transakcja> transakcje = new HashSet<>();

        for (TransakcjaCreateDto transakcjaCreateDto : transakcjaCreateDtoList) {
            Transakcja transakcja = transakcjaMapper.doTransakcji(transakcjaCreateDto);
            transakcje.add(transakcja);
            firma.dodajTransakcje(transakcja);
        }
        Firma.persist(firma);

        return transakcjaMapper.doWieleTransakcjiDto(transakcje);
    }

    public SzczegolyFirmaDto pobierzSzczegolyFirmy(UUID id) {
        Firma firma = getById(id);
        return firmaMapper.toSzczegolyFirmaDto(firma);
    }

    public PodatekDto pobierzPodatekFirmy(int numberOfPoints) {
        Random random = new Random();
        int pointsInCircle = 0;

        for (int i = 0; i < numberOfPoints; i++) {
            double x = random.nextDouble() * 2 - 1; // Losowa wartość x w przedziale [-1, 1)
            double y = random.nextDouble() * 2 - 1; // Losowa wartość y w przedziale [-1, 1)

            if (isInsideCircle(x, y)) {
                pointsInCircle++;
            }
        }

        return new PodatekDto(BigDecimal.valueOf(4.0 * pointsInCircle / numberOfPoints));
    }

    private static boolean isInsideCircle(double x, double y) {
        return x * x + y * y <= 1.0;
    }

    public int hashFirmy(UUID id) {
        Firma firma = getById(id);
        return firma.hashCode();
    }

    @Transactional
    public PlikDto dodajFakture(UUID id, MultipartFormDataInput faktura) {
        try {
            Firma firma = getById(id);
            InputPart inputPart = faktura.getFormDataMap().get("faktura").get(0);
            String sciezka = "faktury/" + id.toString();
            String nazwaPliku = cleanPath(inputPart.getHeaders().getFirst("Content-Disposition").split(";")[2].split("=")[1].replace("\"", ""));
            String typPliku = inputPart.getMediaType().toString();
            String sciezkaPliku = sciezka + "/" + nazwaPliku;

            saveFile(sciezka, nazwaPliku, inputPart);

            Optional<Plik> plik = Plik.find("firma_id = :id AND sciezka = :sciezka", Parameters.with("id", id).and("sciezka", sciezkaPliku)).singleResultOptional();

            if (plik.isEmpty()) {
                Plik nowyPlik = plikMapper.doPlik(sciezkaPliku, typPliku);
                firma.dodajPlik(nowyPlik);
                Firma.persist(firma);
                return plikMapper.doPlikDto(nowyPlik);
            } else {
                return plikMapper.doPlikDto(plik.get());
            }

        } catch (
                IOException e) {
            return null;
        }
    }

    public String cleanPath(String path) {
        return path.replaceAll("\\.\\.", "");
    }


    private void saveFile(String sciezka, String nazwaPliku, InputPart inputPart) throws IOException {
        Path sciezkaKatalogu = Paths.get(sciezka);
        if (!Files.exists(sciezkaKatalogu)) {
            Files.createDirectories(sciezkaKatalogu);
        }

        File plik = new File(sciezkaKatalogu.toString() + "/" + nazwaPliku);
        try (InputStream inputStream = inputPart.getBody(InputStream.class, null)) {
            Files.copy(inputStream, plik.toPath());
        }
    }

    public PlikListDto pobierzFaktury(UUID id) {
        List<byte[]> faktury = new ArrayList<>();
        List<Plik> dostepneFaktury = Plik.listAllByFirmaId(id);

        for (Plik faktura : dostepneFaktury) {
            byte[] fak = pobierzPlik(faktura.getSciezka());
            faktury.add(fak);
        }

        return plikMapper.doPlikListDto(faktury);
    }

    public byte[] pobierzPlik(String sciezka) {
        try {
            Path path = Paths.get(sciezka);
            if (Files.exists(path))
                return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public byte[] pobierzFakture(UUID fakturaId) {
        Plik plik = Plik.findById(fakturaId);
        return plik != null ? pobierzPlik(plik.getSciezka()) : null;
    }

}