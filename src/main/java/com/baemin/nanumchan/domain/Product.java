package com.baemin.nanumchan.domain;

import com.baemin.nanumchan.dto.NearProductDTO;
import com.baemin.nanumchan.support.domain.AbstractEntity;
import com.baemin.nanumchan.utils.DistanceUtils;
import com.baemin.nanumchan.validate.Expirable;
import com.baemin.nanumchan.validate.Currency;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Currency(max = 1000000, unit = Currency.Unit.HUNDRED)
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

    @Size(max = 6)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Order> orders;

    public int getOrdersSize() {
        return orders.size();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Status getStatus() {
        if (isExpiredDateTime()) {
            return Status.EXPIRED;
        }
        if (maxParticipant == getOrdersSize()) {
            return Status.FULL_PARTICIPANTS;
        }
        return Status.ON_PARTICIPATING;
    }

    public boolean isExpiredDateTime() {
        return expireDateTime.isBefore(LocalDateTime.now());
    }

    public boolean compareMaxParticipants(int orderCount) {
        return maxParticipant == orderCount;
    }

    public boolean isSharedDateTime() {
        return shareDateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return expireDateTime;
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return shareDateTime;
    }

    public boolean isOwner(User user) {
        return owner.equalsEmail(user);
    }

    public boolean isStatus_ON_PARTICIPATING() {
        return getStatus().equals(Status.ON_PARTICIPATING);
    }

    public Optional<Order> getOrderByUser(User user) {
        return orders.stream().filter(order -> order.getParticipant().equalsEmail(user)).findAny();
    }

    public NearProductDTO toNearProductDTO(double longitude, double latitude, double ownerRating) {
        return NearProductDTO.builder()
                .distanceMeter(Math.floor(DistanceUtils.distanceInMeter(this.latitude, this.longitude, latitude, longitude)))
                .productId(id)
                .productTitle(title)
                .productImgUrl(productImages.stream().findFirst().isPresent() ? productImages.get(0).getUrl() : null)
                .ownerName(owner.getName())
                .ownerImgUrl(owner.getImageUrl())
                .ownerRating(ownerRating)
                .orderCnt(getOrdersSize())
                .maxParticipant(maxParticipant)
                .expireDateTime(expireDateTime)
                .price(price)
                .build();
    }
}
