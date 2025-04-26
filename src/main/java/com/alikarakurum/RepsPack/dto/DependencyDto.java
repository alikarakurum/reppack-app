package com.alikarakurum.RepsPack.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DependencyDto {

    @JsonProperty("package")
    private String packageName;
    private String version;
}
