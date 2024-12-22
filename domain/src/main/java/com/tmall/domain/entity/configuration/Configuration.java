package com.tmall.domain.entity.configuration;

import com.tmall.domain.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Entity
@Table(name = "CONFIGURATION")
@Getter
@Setter
public class Configuration extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 7085813564053017216L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;
}