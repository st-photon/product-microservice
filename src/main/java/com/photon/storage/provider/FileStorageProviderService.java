package com.photon.storage.provider;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FunctionalInterface
public interface FileStorageProviderService {

    File process(String directory, MultipartFile multipartFile);

    default File renameFile(File fileDir, String oldFileName, String newFileName) {
        if(StringUtils.isBlank(newFileName)) {
            newFileName = String.valueOf(System.currentTimeMillis());
        }
        String ext = FilenameUtils.getExtension(oldFileName);
        String fileName = fileDir + File.separator + newFileName + "." +ext;
        return new File(fileName);
    }
}
