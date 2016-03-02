package com.softserve.hotels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PhotoExtentions")
public class PhotoExtention implements Serializable {

    private static final long serialVersionUID = -6164472591181203473L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "extention", nullable = false, unique = true)
    private String extention;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    @Override
    public String toString() {
        return extention;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PhotoExtention)) {
            return false;
        }
        PhotoExtention photoExtention = (PhotoExtention) obj;
        return this.extention.equals(photoExtention.extention);
    }

    @Override
    public int hashCode() {
        return extention.hashCode();
    }

}
