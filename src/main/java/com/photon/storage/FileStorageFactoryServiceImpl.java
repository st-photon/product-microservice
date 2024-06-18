package com.photon.storage;


import com.photon.storage.provider.FileStorageProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileStorageFactoryServiceImpl implements FileStorageFactoryService {

    private static final List<StorageProviderDTO> STORAGE_PROVIDERS = new ArrayList<>();

    private static final String DEFAULT_STORAGE_PROVIDER = "fileSystemStorage";

    private final ApplicationContext applicationContext;

    static {
        STORAGE_PROVIDERS.add(new StorageProviderDTO("fileSystemStorage", "fileSystemStorageProviderService"));
        STORAGE_PROVIDERS.add(new StorageProviderDTO("awsS3Storage", "s3BucketStorageProviderService"));
    }

    @Override
    public FileStorageResult saveFile(String directory, MultipartFile multipartFile) {
        final Optional<StorageProviderDTO> storageProviderDTO = STORAGE_PROVIDERS.stream().filter(s -> s.getProviderName().equals(DEFAULT_STORAGE_PROVIDER)).findFirst();
        if(storageProviderDTO.isEmpty()) {
            throw new RuntimeException("At least any one storage should be available to save file");
        }
        FileStorageProviderService fileStorageProviderService = (FileStorageProviderService) applicationContext.getBean(storageProviderDTO.get().getProviderBeanName());
        File savedFile = fileStorageProviderService.process(directory, multipartFile);
        return FileStorageResult
                .builder()
                .absolutePath(savedFile.getAbsolutePath())
                .mimeType(FilenameUtils.getExtension(savedFile.getAbsolutePath()))
                .providerType(DEFAULT_STORAGE_PROVIDER)
                .newFileName(FilenameUtils.getName(savedFile.getAbsolutePath()))
                .oldFileName(multipartFile.getOriginalFilename())
                .build();
    }
}
