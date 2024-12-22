package com.tmall.infrastructure.repository.configuration;

import com.tmall.domain.entity.configuration.Configuration;
import com.tmall.domain.repository.configuration.ConfigurationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaConfigurationRepository extends ConfigurationRepository, JpaRepository<Configuration, Long> {
}