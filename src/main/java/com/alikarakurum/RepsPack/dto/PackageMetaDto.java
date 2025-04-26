package com.alikarakurum.RepsPack.dto;


import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder(toBuilder = true)
public class PackageMetaDto {
    private String name;
    private String version;
    private String author;
    private List<DependencyDto> dependencies;
}
