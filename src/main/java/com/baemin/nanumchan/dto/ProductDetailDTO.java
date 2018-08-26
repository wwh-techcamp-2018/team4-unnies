package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.Status;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @DecimalMin("0")
    private Integer orderCount;

    @NotNull
    private Status status;

    @NotNull
    @Min(0)
    @Max(5)
    private Double ownerRating;

}
