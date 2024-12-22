package com.tmall.domain.entity.cart;

import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.cart.item.VasItem;
import com.tmall.domain.entity.common.BaseEntity;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.entity.promotion.Promotable;
import com.tmall.domain.entity.promotion.Promotion;
import com.tmall.domain.enums.CategoryType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "CART")
@Getter
@Setter
public class Cart extends BaseEntity implements Promotable {

    @Serial
    private static final long serialVersionUID = 567448431001742217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Where(clause = "deleted = false")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    private Promotion promotion;

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                        .add(item.getVasItems().stream()
                                .map(vasItem -> vasItem.getPrice().multiply(BigDecimal.valueOf(vasItem.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getFinalTotalPrice() {
        return getTotalPrice().subtract(getTotalDiscount());
    }

    public int getUniqueItemCount() {
        return items.size();
    }

    public int getItemCount() {
        return items.stream().mapToInt(item -> item.getQuantity()
                + item.getVasItems().stream().mapToInt(VasItem::getQuantity).sum()).sum();
    }

    public int getDigitalItemCount() {
        return items.stream()
                .filter(item -> item.getCategory().getCategoryType().equals(CategoryType.DIGITAL))
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public Optional<Item> getItemByProduct(Product product) {
        return getItems().stream().filter(item -> item.getProduct().getId().equals(product.getId())).findFirst();
    }

    public boolean hasItem(Item item) {
        return items.stream().anyMatch(i -> i.getId().equals(item.getId()));
    }

    public BigDecimal getTotalDiscount() {
        return promotion != null ? promotion.getDiscountAmount(this) : BigDecimal.ZERO;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}