package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderProduct")
public class Order extends AbstractEntity {

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_order_product"))
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
    private Status status = Status.ON_SHARING;

    public boolean isCompleteSharing() {
        return status.equals(Status.COMPLETE_SHARING);
    }

    public void changeStatusToCompleted() {
        status = Status.COMPLETE_SHARING;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
