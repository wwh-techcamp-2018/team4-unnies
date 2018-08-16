package com.baemin.nanumchan.product.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProductImage extends AbstractEntity {

//    @ManyToOne
//    private Product product;

    @Column(nullable = false)
    private String url;

    public ProductImage() {
    }

    public ProductImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
