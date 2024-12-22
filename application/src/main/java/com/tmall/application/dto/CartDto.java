package com.tmall.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3297675415110350788L;

    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private Long appliedPromotionId;

    @Builder.Default
    private List<ItemDto> items = new ArrayList<>();
}