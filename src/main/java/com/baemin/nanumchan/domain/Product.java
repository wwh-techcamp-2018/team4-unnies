package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity {

    // TODO: User owner

    @NotEmpty
    private String name;

    // TODO: Location address

    @NotEmpty
    @Length(max = 40)
    private String title;

    // TODO: 1000원 단위 밸리데이터
    @DecimalMin("0")
    private Integer price;

    @NotEmpty
    @Length(max = 2000)
    private String description;

    @DecimalMin("1")
    @DecimalMax("6")
    private Integer maxParticipant;

    @FutureOrPresent
    @DateTimeFormat
    private LocalDateTime expireDateTime;

    @FutureOrPresent
    @DateTimeFormat
    private LocalDateTime shareDateTime;

    @Column(nullable = false)
    private boolean isBowlNeeded;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> productImages;
}
