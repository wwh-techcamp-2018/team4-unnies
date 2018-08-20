package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NonNull;

import javax.persistence.*;

@Entity
public class ProductImage extends AbstractEntity {

    @NonNull
    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_productImage_product"))
    private Product product;

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
