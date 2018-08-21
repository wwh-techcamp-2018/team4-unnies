package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_product"))
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_writer"))
    private User writer;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Override
    public String toString() {
        return "Review{" +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
