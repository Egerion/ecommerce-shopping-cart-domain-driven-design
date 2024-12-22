package com.tmall.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VasItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6668800496163519946L;

    private Long vasItemId;
    private Long vasSellerId;
    private Long vasCategoryId;
    private BigDecimal price;
    private int quantity;
}