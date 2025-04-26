package com.alikarakurum.RepsPack.util;

import com.alikarakurum.RepsPack.dto.PackageMetaDto;
import com.alikarakurum.RepsPack.model.Dependency;
import com.alikarakurum.RepsPack.model.PackageMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

public class MetadataMapper {

    private final ObjectMapper objectMapper;

    public MetadataMapper(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }


    public PackageMetadata mapPackageMetadata(MultipartFile metadataFile) throws Exception {

        try{
            PackageMetaDto metaDTO = objectMapper.readValue(metadataFile.getBytes(), PackageMetaDto.class);
            PackageMetadata metadataObj = PackageMetadata.builder()
                    .name(metaDTO.getName())
                    .version(metaDTO.getVersion())
                    .author(metaDTO.getAuthor())
                    .dependencies(metaDTO.getDependencies().stream().map(dep ->
                            Dependency.builder()
                                    .packageName(dep.getPackageName())
                                    .version(dep.getVersion())
                                    .build()).collect(Collectors.toList()))
                    .build();
            metadataObj.getDependencies().forEach(dp -> dp.setPackageMetadata(metadataObj));
            return metadataObj;
        } catch (Exception e) {
            throw new Exception("Exception occurred while parsing the metadata file "+e.getMessage(), e);
        }


    }


}
