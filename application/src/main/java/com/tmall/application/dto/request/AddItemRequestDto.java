package com.tmall.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class AddItemRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6674923797201171062L;

    private Long itemId;
    private Long categoryId;
    private Long sellerId;
    private BigDecimal price;
    private int quantity;
}
