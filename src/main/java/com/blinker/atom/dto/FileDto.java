package com.blinker.atom.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class FileDto {
    private String name;
    private String url;
    @Builder.Default
    private List<String> tempPaths = new ArrayList<>();
    private String size;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileDto)) {
            return false;
        }
        FileDto fileDto = (FileDto) o;
        return name.equals(fileDto.name) && url.equals(fileDto.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
