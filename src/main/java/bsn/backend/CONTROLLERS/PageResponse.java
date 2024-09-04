package bsn.backend.CONTROLLERS;

import lombok.*;

import java.util.List;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<BookResponse> content;
    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

}
