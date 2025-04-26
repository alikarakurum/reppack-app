package com.alikarakurum.RepsPack.controller;



import com.alikarakurum.RepsPack.service.PackageMetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.storage.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Objects;




@Tag(
        name = "Package Controller",
        description = "This API is created for uploading and downloading .rep packages to repository storage. " +
                "Make sure that the uploading package file is .rep formatted and also metadata.json is provided inside the form-data as request body." +
                "Keys inside the form-data must be 'package' and 'metadata' "
)
@RequestMapping("/")
@RestController
public class PackageController {


    private final StorageService storageService;
    private final PackageMetadataService packageMetadataService;

    PackageController(StorageService storageService, PackageMetadataService packageMetadataService) {
        this.storageService = storageService;
        this.packageMetadataService = packageMetadataService;
    }





    @Operation(
            summary = "Upload package to storage as .rep file with metadata.json",
            description = "Upload files to storage by package name, version and files. " +
                    "Files must include .rep file of package and metadata. Please be sure that the metadata is properly configured. " +
                    "BODY MUST BE FORM-DATA and the keys of the files must be 'package' and 'metadata' ",
            parameters = {
                    @Parameter(name = "packageName", description = "The name of the package", required = true),
                    @Parameter(name = "version", description = "The version of the package", required = true),
            },
            requestBody = @RequestBody(description = "Provide form-data to body. Keys must be defined as 'package' and 'metadata'. Package file as .rep file and metadata as json file",
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = MultipartFile.class)
                    ),required = true),
            responses = {
                    @ApiResponse(
                            description = "Uploaded rep package file",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Package.class))
                    )
            }
    )

    @PostMapping("{packageName}/{version}")
    public ResponseEntity<?> upload(@PathVariable String packageName, @PathVariable String version,
                                    @RequestPart("package") MultipartFile file,
                                    @RequestPart("metadata") MultipartFile metadata)
    {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".rep") || !Objects.requireNonNull(metadata.getOriginalFilename()).endsWith(".json")) {
            return ResponseEntity.badRequest().body("Invalid file types. Expected: .rep and .json");
        }
        if ( file.isEmpty() || metadata.isEmpty() ) {
            return ResponseEntity.badRequest().body("Missing 'package' or 'metadata' parts");
        }

        try
        {
            byte[] bytes = file.getBytes();
            storageService.upload(packageName,version,file.getOriginalFilename(),bytes);
            packageMetadataService.save(metadata);
            return ResponseEntity.status(HttpStatus.CREATED).body("Package uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }





    @Operation(
            summary = "Download package from storage as .rep file",
            description = "Download package from storage by package name, version and file name of package",
            parameters = {
                    @Parameter(name = "packageName", description = "The name of the package", required = true),
                    @Parameter(name = "version", description = "The version of the package", required = true),
                    @Parameter(name= "filename",description = "The name of the .rep package file",required = true)
            },
            responses = {
                    @ApiResponse(
                            description = "Downloaded rep package file",
                            content = @Content(mediaType = "application/octet-stream")
                    )
            }
    )

    @GetMapping("{packageName}/{version}/{filename}")
    public ResponseEntity<?> download(@PathVariable String packageName, @PathVariable String version, @PathVariable String filename)
    {
        try{
            byte[] file = storageService.download(packageName,version,filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
