package com.alikarakurum.RepsPack.service;

import com.alikarakurum.RepsPack.model.PackageMetadata;
import com.alikarakurum.RepsPack.repository.PackageMetadataRepository;
import com.alikarakurum.RepsPack.util.MetadataMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
public class PackageMetadataService {

    private final PackageMetadataRepository packageMetadataRepository;
    private final MetadataMapper metadataMapper;

    public PackageMetadataService(PackageMetadataRepository packageMetadataRepository) {
        this.packageMetadataRepository = packageMetadataRepository;
        this.metadataMapper = new MetadataMapper(new ObjectMapper());
    }

    public void save(MultipartFile metadataFile) throws Exception {
        PackageMetadata metadataObj = metadataMapper.mapPackageMetadata(metadataFile);
        try{
            packageMetadataRepository.save(metadataObj);
        } catch (Exception e) {
            throw new Exception("Exception occured while saving the metadata information to the database "+e.getMessage(),e);
        }
    }
}
