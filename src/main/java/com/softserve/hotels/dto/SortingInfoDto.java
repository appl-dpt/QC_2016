package com.softserve.hotels.dto;

public class SortingInfoDto {
    private String sortingField;
    private Boolean isAscending;
    
    public SortingInfoDto(String sortingField, Boolean isAscending) {
        this.sortingField = sortingField;
        this.isAscending = isAscending;
    }

    /**
     * @return the sortingField
     */
    public String getSortingField() {
        return sortingField;
    }

    /**
     * @param sortingField the sortingField to set
     */
    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    /**
     * @return the isAscending
     */
    public Boolean getIsAscending() {
        return isAscending;
    }

    /**
     * @param isAscending the isAscending to set
     */
    public void setIsAscending(Boolean isAscending) {
        this.isAscending = isAscending;
    }
    
}
