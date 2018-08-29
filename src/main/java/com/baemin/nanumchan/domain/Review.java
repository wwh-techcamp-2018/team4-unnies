package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.exception.NotAllowedException;
import com.baemin.nanumchan.support.domain.AbstractEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review extends AbstractEntity {

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_product"))
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_writer"))
    private User writer;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_chef"))
    private User chef;

    @NotNull
    @Length(min = 10, max = 2000)
    @Column(nullable = false)
    private String comment;

    @NotNull
    @Range(min = 0, max = 5)
    @Column(nullable = false)
    private Double rating;

    public Review(User user, Product product, String comment, Double rating) {
        this.comment = comment;
        this.rating = rating;

        canCreateReview(product, user);

        this.writer = user;
        this.product = product;
        this.chef = product.getOwner();
    }

    private void canCreateReview(Product product, User user) {
        Order order = product.getOrderByUser(user);

        if (order == null) {
            throw new EntityNotFoundException("신청 내역이 존재하지 않습니다");
        }

        if (!order.isCompleteSharing()) {
            throw new NotAllowedException("나눔 완료가 되지 않았습니다");
        }
    }
}
