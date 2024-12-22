package com.tmall.domain.repository.configuration;

import com.tmall.domain.entity.configuration.Configuration;

public interface ConfigurationRepository {
    Configuration findByKeyAndDeletedFalse(String key);
}