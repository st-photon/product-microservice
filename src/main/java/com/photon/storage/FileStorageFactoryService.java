package com.photon.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageFactoryService {

    FileStorageResult saveFile(String directory, MultipartFile multipartFile);
}
