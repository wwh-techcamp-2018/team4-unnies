package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderProduct")
public class Order extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_product"))
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_participant"))
    private User participant;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ON_SHARING;

}
