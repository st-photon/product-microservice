package com.photon.storage;


public class StorageProviderDTO {

    private final String providerName;

    private final String providerBeanName;

    public StorageProviderDTO(String providerName, String providerBeanName) {
        this.providerName = providerName;
        this.providerBeanName = providerBeanName;
    }

    public String getProviderName() {
        return providerName;
    }


    public String getProviderBeanName() {
        return providerBeanName;
    }
}
