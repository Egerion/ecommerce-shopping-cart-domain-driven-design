package com.tmall.application.dto.response;

import com.tmall.application.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto extends RepresentationModel<CartResultResponseDto> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4771629623852600172L;

    private boolean result;
    private String message;
    private CartDto cart;
}