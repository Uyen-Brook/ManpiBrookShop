package com.manpibrook.backend_api.entity.enums;

public enum EUploadType {
    PROFILE("profiles", "PRFL"),
    LAPTOP("laptops", "LTP"),
    BRAND("brands", "BRD"),
    PRODUCT("products","PRT"),
    BANNER("banners", "BNR");

    private final String folder;
    private final String prefix;

    EUploadType(String folder, String prefix) {
        this.folder = folder;
        this.prefix = prefix;
    }

    public String getFolder() { return folder; }
    public String getPrefix() { return prefix; }
}
