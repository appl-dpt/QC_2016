package com.softserve.hotels.dto;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.softserve.hotels.model.ActionStatus;
import com.softserve.hotels.model.User;

public class ActiveOrders {

    private static final int PAGES_RANGE = 5;

    private User tenant;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String name;

    private int startPage = 1;
    private int endPage = PAGES_RANGE;
    private int pageCount;
    private int currentPage = 1;
    
    private ActionStatus actionStatus;

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public User getTenant() {
        return tenant;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        startPage = (currentPage % PAGES_RANGE == 0) ? currentPage - (PAGES_RANGE - 1)
                : ((currentPage / PAGES_RANGE) * PAGES_RANGE + 1);
        endPage = (startPage + PAGES_RANGE) - 1;
        if (endPage >= pageCount) {
            endPage = pageCount;
        }

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPagesRange() {
        return PAGES_RANGE;
    }
    
    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }
    
    public ActionStatus getActionStatus() {
        return actionStatus;
    }
        
}
