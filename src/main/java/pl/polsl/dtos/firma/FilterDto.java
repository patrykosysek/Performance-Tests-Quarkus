package pl.polsl.dtos.firma;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterDto {
    private Integer page = 0;
    private Integer size = 20;
    private String search = "";
}