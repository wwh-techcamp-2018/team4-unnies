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
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_owner"))
    private User owner;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @NotEmpty
    private String name;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @NotEmpty
    @Length(max = 40)
    private String title;

    @PositiveOrZero
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

    public boolean isExpiredDateTime() {
        return expireDateTime.isBefore(LocalDateTime.now());
    }

    public boolean compareMaxParticipants(Integer orderCount) {
        return maxParticipant.equals(orderCount);
    }

    public Status calculateStatus(Integer orderCount) {
        if (isExpiredDateTime()) {
            return Status.EXPIRED;
        }
        if (compareMaxParticipants(orderCount)) {
            return Status.FULL_PARTICIPANTS;
        }
        return Status.ON_PARTICIPATING;

    }

}
