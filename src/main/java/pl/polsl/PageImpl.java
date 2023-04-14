package pl.polsl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PageImpl<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;

    public PageImpl(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

}
