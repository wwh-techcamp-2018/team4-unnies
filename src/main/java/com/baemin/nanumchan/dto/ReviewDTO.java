package com.baemin.nanumchan.dto;

import com.baemin.nanumchan.domain.Product;
import com.baemin.nanumchan.domain.Review;
import com.baemin.nanumchan.domain.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull
    @Length(min = 10, max = 2000)
    private String comment;

    @NotNull
    @Range(min = 0, max = 5)
    private Double rating;

    public Review toEntity(User user, Product product) {
        return new Review(user, product, comment, rating);
    }
}
