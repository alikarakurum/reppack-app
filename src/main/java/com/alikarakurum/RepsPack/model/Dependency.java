package com.alikarakurum.RepsPack.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="dependencies")
@Builder(toBuilder = true)
public class Dependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Dependency package name cannot be null ! - Check for dependencies at Metadata file")
    @Pattern(regexp = "\\d+\\.\\d+\\.\\d+", message = "Version of dependency must be in format X.Y.Z  - Check for dependencies at Metadata files")
    private String packageName;

    @NotBlank(message = "Dependency version cannot be null ! - Check for dependencies at Metadata file")
    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private PackageMetadata packageMetadata;
}
