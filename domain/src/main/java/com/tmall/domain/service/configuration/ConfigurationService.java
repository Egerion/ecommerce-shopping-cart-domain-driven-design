package com.tmall.domain.service.configuration;

import com.tmall.domain.entity.configuration.Configuration;
import com.tmall.domain.repository.configuration.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final Map<String, Configuration> configMap = new HashMap<>();

    public int getAsInteger(String key) {
        try {
            return Integer.parseInt(getValue(key));
        } catch (Exception e) {
            throw new RuntimeException("Occurred an error while getting configuration as integer with key:" + key);
        }
    }

    public String getAsString(String key) {
        try {
            return getValue(key);
        } catch (Exception e) {
            throw new RuntimeException("Occurred an error while getting configuration as string with key:" + key);
        }
    }

    private String getValue(String key) {
        Configuration configuration = configMap.computeIfAbsent(key, configurationRepository::findByKeyAndDeletedFalse);
        return configuration.getValue();
    }
}