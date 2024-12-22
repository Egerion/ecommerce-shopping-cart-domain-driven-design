package com.tmall.application.controller;

import com.tmall.application.dto.request.AddItemRequestDto;
import com.tmall.application.dto.request.AddVasItemRequestDto;
import com.tmall.application.dto.response.CartResponseDto;
import com.tmall.application.dto.response.CartResultResponseDto;
import com.tmall.application.service.CartApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tmall.application.constants.EndPointConstants.V1_PATH;
import static com.tmall.application.controller.CartController.END_POINT;


@Tag(name = END_POINT)
@RestController
@RequestMapping(V1_PATH + END_POINT)
@RequiredArgsConstructor
public class CartController {

    static final String END_POINT = "/shopping-carts";

    private final CartApplicationService cartService;

    @Operation(description = "Return Buyer Cart Information")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch buyer cart",
                    content = @Content(schema = @Schema(implementation = CartResponseDto.class))
            )
    })
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDto> displayCart(@PathVariable Long cartId) {
        CartResponseDto response = cartService.displayCart(cartId);

        addLinks(response, cartId, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).displayCart(cartId)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Add Item to Cart")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Add Item to cart",
                    content = @Content(schema = @Schema(implementation = CartResultResponseDto.class))
            )
    })
    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<CartResultResponseDto> addItem(@PathVariable Long cartId, @RequestBody AddItemRequestDto request) {
        CartResultResponseDto response = cartService.addItemToCart(cartId, request);

        addLinks(response, cartId, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).addItem(cartId, request)).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Add VasItem to Cart")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Add VasItem to cart",
                    content = @Content(schema = @Schema(implementation = CartResultResponseDto.class))
            )
    })
    @PostMapping("/{cartId}/add-vasItem")
    public ResponseEntity<CartResultResponseDto> addVasItem(@PathVariable Long cartId, @RequestBody AddVasItemRequestDto request) {
        CartResultResponseDto response = cartService.addVasItemToCart(cartId, request);

        addLinks(response, cartId, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).addVasItem(cartId, request)).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Remove Item from Cart")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "remove item from cart",
                    content = @Content(schema = @Schema(implementation = CartResultResponseDto.class))
            )
    })
    @DeleteMapping("/{cartId}/{itemId}")
    public ResponseEntity<CartResultResponseDto> deleteItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        CartResultResponseDto response = cartService.removeItemFromCart(cartId, itemId);

        addLinks(response, cartId, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).deleteItem(cartId, itemId)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Reset Cart")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "reset cart, flushes all item and promotion from given cart",
                    content = @Content(schema = @Schema(implementation = CartResultResponseDto.class))
            )
    })
    @DeleteMapping("/{cartId}/reset")
    public ResponseEntity<CartResultResponseDto> reset(@PathVariable Long cartId) {
        CartResultResponseDto response = cartService.resetCart(cartId);

        addLinks(response, cartId, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).reset(cartId)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private static void addLinks(RepresentationModel<?> response, Long cartId, Link selfLink) {
        response.add(selfLink);
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).displayCart(cartId)).withRel("displayCart"));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).reset(cartId)).withRel("reset"));
    }
}