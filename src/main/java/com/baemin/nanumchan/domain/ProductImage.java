package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductImage extends AbstractEntity {

    @NonNull
    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_productImage_product"))
    private Product product;

}
