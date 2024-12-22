package com.tmall.domain.entity.cart.item;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.category.Category;
import com.tmall.domain.entity.common.BaseEntity;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.seller.Seller;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
public class Item extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -880629120739173766L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Where(clause = "deleted = false")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VasItem> vasItems = new ArrayList<>();

    @Column(name = "QUANTITY")
    private int quantity;

    public Seller getSeller() {
        return product.getSeller();
    }

    public Category getCategory() {
        return product.getCategory();
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public int getTotalVasItemCount() {
        return vasItems.stream().mapToInt(VasItem::getQuantity).sum();
    }

    public Optional<VasItem> getVasItemByProduct(Product product) {
        return getVasItems().stream().filter(item -> item.getProduct().getId().equals(product.getId())).findFirst();
    }
}