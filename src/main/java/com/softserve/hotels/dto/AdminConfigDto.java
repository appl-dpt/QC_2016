package com.softserve.hotels.dto;

public class AdminConfigDto {
    
    private Integer maxPhoto;
    private Integer maxUploadSize;

    public Integer getMaxPhoto() {
        return maxPhoto;
    }

    public void setMaxPhoto(Integer maxPhoto) {
        this.maxPhoto = maxPhoto;
    }

    public Integer getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(Integer maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

}
