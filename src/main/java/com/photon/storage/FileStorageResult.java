package com.photon.storage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileStorageResult {

    private String newFileName;

    private String oldFileName;

    private String mimeType;

    private String absolutePath;

    private String providerType;

}
