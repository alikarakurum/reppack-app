package com.alikarakurum.RepsPack.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="packages")
@Builder(toBuilder = true)
public class PackageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long id;

    @NotBlank(message = "Name property cannot be null ! - Check for Metadata file")
    @Pattern(regexp = "\\d+\\.\\d+\\.\\d+", message = "Version must be in format X.Y.Z")
    private String name;

    @NotBlank(message = "Version property cannot be null ! - Check for Metadata file")
    private String version;

    @NotBlank(message = "Author property cannot be null ! - Check for Metadata file")
    private String author;

    @OneToMany(mappedBy = "packageMetadata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependency> dependencies;
}
