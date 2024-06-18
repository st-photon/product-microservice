package com.photon.storage.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("fileSystemStorageProviderService")
@Slf4j
@RequiredArgsConstructor
public class FileSystemStorageProviderService implements FileStorageProviderService {

    private static final String ROOT_DIRECTORY = System.getProperty("user.dir");

    @Override
    public File process(String directory, MultipartFile multipartFile) {
        try {
            String path = ROOT_DIRECTORY + File.separator + directory;
            final File destinationPath = new File(path);
            if(!destinationPath.exists()) {
                boolean isDirCreated = destinationPath.mkdirs();
                if(!isDirCreated) {
                    throw new IllegalArgumentException("Failed to create directory");
                }
            }
            multipartFile.transferTo(destinationPath);
            return renameFile(destinationPath, multipartFile.getOriginalFilename(), null);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Destination path not found to upload file %s", e.getMessage()));
        }
    }
}
