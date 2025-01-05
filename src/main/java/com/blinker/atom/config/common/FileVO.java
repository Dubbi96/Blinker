package com.blinker.atom.config.common;

import com.blinker.atom.dto.FileDto;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class FileVO {
    private String name;
    private String url;

    public static FileVO from(FileDto fileDto) {
        return FileVO.builder()
                .name(fileDto.getName())
                .url(fileDto.getUrl())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileVO)) {
            return false;
        }
        FileVO fileVO = (FileVO) o;
        return name.equals(fileVO.name) && url.equals(fileVO.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
