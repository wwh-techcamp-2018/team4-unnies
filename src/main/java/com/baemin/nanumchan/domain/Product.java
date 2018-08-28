package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.baemin.nanumchan.validate.Expirable;
import com.baemin.nanumchan.validate.KoreanWon;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Expirable
public class Product extends AbstractEntity implements DateTimeExpirable {

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    @Setter
    private User owner;

    @Nullable
    @Size(max = 5)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    @Setter
    private List<ProductImage> productImages;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @Setter
    private Category category;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @NotNull
    @KoreanWon(max = 1000000, unit = KoreanWon.Unit.HUNDRED)
    @Column(nullable = false)
    private Integer price;

    @NotEmpty
    @Length(max = 100000)
    @Lob
    @Column(nullable = false, length = 100000)
    private String description;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String address;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String addressDetail;

    @NotNull
    @DecimalMin("-90.00000")
    @DecimalMax("90.00000")
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @DecimalMin("-180.00000")
    @DecimalMax("180.00000")
    @Column(nullable = false)
    private Double longitude;

    @NotNull
    @Range(min = 1, max = 6)
    @Column(nullable = false)
    private Integer maxParticipant;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime expireDateTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime shareDateTime;

    @NotNull
    @Column(nullable = false)
    private Boolean isBowlNeeded;

    public boolean isExpiredDateTime() {
        return expireDateTime.isBefore(LocalDateTime.now());
    }

    public boolean compareMaxParticipants(int orderCount) {
        return maxParticipant == orderCount;
    }

    public Status calculateStatus(int orderCount) {
        if (isExpiredDateTime()) {
            return Status.EXPIRED;
        }
        if (compareMaxParticipants(orderCount)) {
            return Status.FULL_PARTICIPANTS;
        }
        return Status.ON_PARTICIPATING;
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return expireDateTime;
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return shareDateTime;
    }

}
