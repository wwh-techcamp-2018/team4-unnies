package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.DeliveryType;
import com.baemin.nanumchan.domain.Order;
import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    private DeliveryType deliveryType;

    public Order toEntity(User user, Product product) {
        return new Order(user, product, this.deliveryType);
    }
}
