package com.tmall.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class AddVasItemRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1910368279185326869L;

    private Long itemId;
    private Long vasItemId;
    private Long vasCategoryId;
    private Long vasSellerId;
    private BigDecimal price;
    private int quantity;
}
