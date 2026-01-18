package com.manpibrook.backend_api.entity.enums;

public enum EUploadType {
	BRAND("brands"),
    PROFILE("profiles"),
    LAPTOP("laptops");

    private final String folder;

    EUploadType(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
