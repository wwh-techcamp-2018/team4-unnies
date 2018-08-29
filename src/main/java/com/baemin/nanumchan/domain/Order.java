package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.exception.NotAllowedException;
import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderProduct")
public class Order extends AbstractEntity {

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_order_product"))
    @JsonBackReference
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_order_participant"))
    private User participant;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status shareStatus = Status.ON_SHARING;

    public Order(@NotNull User participant, @NotNull Product product, @NotNull DeliveryType deliveryType) {
        this.participant = participant;
        this.deliveryType = deliveryType;

        canOrder(product);
        this.product = product;
    }

    public LocalDateTime getShareDateTime() {
        return product.getShareDateTime();
    }

    public boolean isCompleteSharing() {
        return shareStatus.equals(Status.COMPLETE_SHARING);
    }

    public void changeShareStatus(Status shareStatus) {
        this.shareStatus = shareStatus;
    }

    private void canOrder(Product product) {
        if (product.isOwner(participant)) {
            throw new NotAllowedException("본인은 나눔신청이 안됩니다");
        }
        if (!(product.getOrderByUser(participant) == (null))) {
            throw new NotAllowedException("이미 신청한 사람은 안됩니다");
        }
        if (!product.isStatus_ON_PARTICIPATING()) {
            throw new NotAllowedException(product.getStatus().name());
        }
    }
}
