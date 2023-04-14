package pl.polsl.controllers;


import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pl.polsl.PageImpl;
import pl.polsl.dtos.firma.*;
import pl.polsl.dtos.plik.PlikDto;
import pl.polsl.dtos.plik.PlikListDto;
import pl.polsl.dtos.transakcja.TransakcjaCreateDto;
import pl.polsl.dtos.transakcja.TransakcjaDto;
import pl.polsl.dtos.transakcja.WieleTransakcjiCreateDto;
import pl.polsl.dtos.transakcja.WieleTransakcjiDto;
import pl.polsl.services.FirmaService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/api/firma")
public class FirmaController {
    @Inject
    FirmaService firmaService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public FirmaDto stworzFirme(@Valid FirmaCreateDto dto) {
        return firmaService.stworzFirme(dto);
    }

    @GET
    public PageImpl<FirmaDto> pobierzFirmy(@QueryParam("page") int page, @QueryParam("size") int size, @QueryParam("sort") String sort, @QueryParam("search") String search) {
        return firmaService.pobierzFirmy(new FilterDto(page, size, search));
    }

    @GET
    @Path("/{id}")
    public SzczegolyFirmaDto pobierzSzczegolyFirmy(@PathParam("id") UUID id) {
        return firmaService.pobierzSzczegolyFirmy(id);
    }

    @GET
    @Path("/podatek")
    public PodatekDto pobierzPodatekFirmy(@QueryParam("iteracje") int iteracje) {
        return firmaService.pobierzPodatekFirmy(iteracje);
    }

    @GET
    @Path("/{id}/hash")
    public int hashFirmy(@PathParam("id") UUID id) {
        return firmaService.hashFirmy(id);
    }

    @PATCH
    @Path("/{id}")
    public FirmaDto zaktualizujFirme(@PathParam("id") UUID id, FirmaUpdateDto dto) {
        return firmaService.zaktualizujFirme(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void usunFirme(@PathParam("id") UUID id) {
        firmaService.usunFirme(id);
    }

    @POST
    @Path("/{id}/transakcja")
    public TransakcjaDto dodajTransakcje(@PathParam("id") UUID id, @Valid TransakcjaCreateDto dto) {
        return firmaService.dodajTransakcje(id, dto);
    }

    @POST
    @Path("/{id}/wiele-transakcji")
    public WieleTransakcjiDto dodajWieleTransakcji(@PathParam("id") UUID id, @Valid WieleTransakcjiCreateDto dto) {
        return firmaService.dodajWieleTransakcji(id, dto);
    }

    @POST
    @Path("/{id}/faktura")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public PlikDto dodajFakture(@PathParam("id") UUID id, @MultipartForm MultipartFormDataInput faktura) {
        return firmaService.dodajFakture(id, faktura);
    }

    @GET
    @Path("/{id}/faktura")
    public PlikListDto pobierzFaktury(@PathParam("id") UUID id) {
        return firmaService.pobierzFaktury(id);
    }

    @GET
    @Path("/faktura/{fakturaId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] pobierzFakture(@PathParam("fakturaId") UUID fakturaId) {
        return firmaService.pobierzFakture(fakturaId);
    }
}