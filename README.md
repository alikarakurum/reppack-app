# RepPack - Package Management System

RepPack is a Spring Boot-based API designed for managing .rep packages, allowing for easy uploading and downloading of `.rep` files along with their associated metadata in JSON format. 
## Features

- Upload `.rep` packages with metadata
- Download `.rep` packages


## API Endpoints
#### FOR FURTHER API DOCUMENTATION REFER TO ENDPOINT: /v3/api-docs   

### Upload a Package

Upload a `.rep` package file along with a `metadata.json` file.

**POST** `/api/{packageName}/{version}`

#### Parameters:
- `packageName`: The name of the package (required)
- `version`: The version of the package (required)

#### Request Body:

##### Must be form-data 
###### 'KEY' : 'FILE'
- `package`: `.rep` file (required) Simply a zip file with renamed extension .rep
- `metadata`: `metadata.json` file (required)

#### Example Request:
```bash
POST /api/ExamplePackage/1.0
Content-Type: multipart/form-data
Body: 
    - package: file.rep
    - metadata: metadata.json
```

### Download a Package

Download a .rep package by specifying the package name, version, and file name.

**GET** `/api/{packageName}/{version}/{filename}`
Parameters:

- `packageName`: The name of the package (required)
- `version`: The version of the package (required)
- `filename`:  The file name of the .rep package (required)

#### Example Request:
```bash
GET /api/ExamplePackage/1.0/file.rep
```


## ENVIRONMENT VARIABLES
ENVIRONMENT VARIABLES MUST BE PROVIDED WHILE STARTING A CONTAINERIZED VERSION OF REPPACKAPP
#### STORAGE-STRATEGY 
- file-system
- object-storage

Use object-storage for Minio based storage solution.

#### Example environment variables configuration

      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/{dbName}
      SPRING_DATASOURCE_USERNAME: {your-username}
      SPRING_DATASOURCE_PASSWORD: {your-password}
      MINIO_URL: http://minio:9000
      MINIO_ACCESS_KEY: {your-access-key}
      MINIO_SECRET_KEY: {your-secret-key}
      STORAGE_STRATEGY: file-system

