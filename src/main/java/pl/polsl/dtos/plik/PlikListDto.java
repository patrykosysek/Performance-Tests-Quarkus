package pl.polsl.dtos.plik;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlikListDto {
    List<byte[]> faktury;
}