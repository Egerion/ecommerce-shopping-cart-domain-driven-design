package com.tmall.domain.entity.category;

import com.tmall.domain.entity.common.BaseEntity;
import com.tmall.domain.enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
public class Category extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -5444970108898487226L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private CategoryType categoryType;
}