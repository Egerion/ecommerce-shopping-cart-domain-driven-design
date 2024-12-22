package com.tmall.domain.entity.product;

import com.tmall.domain.entity.category.Category;
import com.tmall.domain.entity.common.BaseEntity;
import com.tmall.domain.entity.seller.Seller;
import com.tmall.domain.enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class Product extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -984263717768244412L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Seller seller;

    @ManyToOne
    private Category category;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "STOCK")
    private int stock;

    public boolean isDigitalCategory() {
        return CategoryType.DIGITAL.equals(getCategory().getCategoryType());
    }
}