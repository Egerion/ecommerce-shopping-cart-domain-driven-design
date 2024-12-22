package com.tmall.domain.entity.promotion;

import com.tmall.domain.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "PROMOTION")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Promotion extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4513979617667125415L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "NAME")
    protected String name;

    public abstract BigDecimal getDiscountAmount(Promotable promotable);
}
