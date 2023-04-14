package pl.polsl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Waluta {
    USD(4.0),
    EUR(4.5),
    YANG(1.22);

    private final Double kurs;
}
