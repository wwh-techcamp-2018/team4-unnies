package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.Status;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {

    @NotNull
    private Product product;
    @NotNull
    private Integer orderCount;
    @NotNull
    private Status status;

    private Double ownerRating;

}
