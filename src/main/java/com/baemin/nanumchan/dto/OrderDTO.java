package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.DeliveryType;
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
}
