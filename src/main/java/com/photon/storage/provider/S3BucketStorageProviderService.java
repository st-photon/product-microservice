package com.photon.storage.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service("s3BucketStorageProviderService")
@Slf4j
@RequiredArgsConstructor
public class S3BucketStorageProviderService implements FileStorageProviderService {

    @Override
    public File process(String directory, MultipartFile multipartFile) {
        return null;
    }
}
