package com.alikarakurum.RepsPack.config;


import org.example.storage.FileSystemStorage;
import org.example.storage.ObjectStorage;
import org.example.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {


    @Value("${storage.strategy}")
    private String strategy;

    @Value("${storage.path}")
    private String objectStorage;


    @Value("${storage.osEndpoint}")
    private String osEndpoint;

    @Value("${storage.osAccessKey}")
    private String osAccessKey;

    @Value("${storage.osSecretKey}")
    private String osSecretKey;


    @Bean
    public StorageService storageStrategy() {
        try
        {
            if ("file-system".equals(strategy)) {
                return new FileSystemStorage(objectStorage);
            }
            else if ("object-storage".equals(strategy)) {
                return new ObjectStorage(osEndpoint, osAccessKey, osSecretKey);
            }
            else {
                throw new IllegalArgumentException("Invalid storage strategy: " + strategy);
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occured while creating the storage service "+e.getMessage() ,e);
        }

    }
}
