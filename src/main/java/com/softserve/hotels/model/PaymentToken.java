package com.softserve.hotels.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT_TOKENS")
public class PaymentToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = Reserved.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "reserved_id")
    private Reserved reserved;

    @Column(name = "approvement_url")
    private String approvementUrl;

    @Column(name = "execute_url")
    private String executeUrl;

    @Column(name = "payer_id")
    private String payerId;

    @Column(name = "access_token")
    private String accessToken;

    /**
     * @return the payerId
     */
    public String getPayerId() {
        return payerId;
    }

    /**
     * @param payerId
     *            the payerId to set
     */
    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    /**
     * @return the executeUrl
     */
    public String getExecuteUrl() {
        return executeUrl;
    }

    /**
     * @param executeUrl
     *            the executeUrl to set
     */
    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }

    /**
     * @return the approvementUrl
     */
    public String getApprovementUrl() {
        return approvementUrl;
    }

    /**
     * @param approvementUrl
     *            the approvementUrl to set
     */
    public void setApprovementUrl(String approvementUrl) {
        this.approvementUrl = approvementUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Reserved getReserved() {
        return reserved;
    }

    public void setReserved(Reserved reserved) {
        this.reserved = reserved;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken
     *            the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
