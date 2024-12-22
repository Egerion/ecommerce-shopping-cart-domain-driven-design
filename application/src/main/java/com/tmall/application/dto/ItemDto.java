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
public class ItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6974641133769533756L;

    private Long itemId;
    private Long categoryId;
    private Long sellerId;
    private BigDecimal price;
    private int quantity;

    @Builder.Default
    private List<VasItemDto> vasItems = new ArrayList<>();
}
