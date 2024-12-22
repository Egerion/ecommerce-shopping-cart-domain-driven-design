package com.tmall.domain.service.configuration;

import com.tmall.domain.entity.configuration.Configuration;
import com.tmall.domain.repository.configuration.ConfigurationRepository;
import com.tmall.domain.service.configuration.ConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService configurationService;

    @Mock
    private ConfigurationRepository configurationRepository;

    private Configuration configuration;

    @Before
    public void setUp() {
        configuration = new Configuration();
        configuration.setKey("sampleKey");
        configuration.setValue("42");
    }

    @Test
    public void shouldGetAsInteger() {
        String key = "sampleKey";

        when(configurationRepository.findByKeyAndDeletedFalse(key)).thenReturn(configuration);

        int result = configurationService.getAsInteger(key);

        assertEquals(42, result);
        verify(configurationRepository, times(1)).findByKeyAndDeletedFalse(key);
    }

    @Test
    public void shouldThrowExceptionWhenKeyNotFoundAsInteger() {
        String missingKey = "missingKey";

        when(configurationRepository.findByKeyAndDeletedFalse(missingKey)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            configurationService.getAsInteger(missingKey);
        });

        assertEquals("Occurred an error while getting configuration as integer with key:" + missingKey, exception.getMessage());
    }

    @Test
    public void shouldGetAsString() {
        String key = "sampleKey";

        when(configurationRepository.findByKeyAndDeletedFalse(key)).thenReturn(configuration);

        String result = configurationService.getAsString(key);

        assertEquals("42", result);
        verify(configurationRepository, times(1)).findByKeyAndDeletedFalse(key);
    }

    @Test
    public void shouldThrowExceptionWhenKeyNotFoundAsString() {
        String missingKey = "missingKey";

        when(configurationRepository.findByKeyAndDeletedFalse(missingKey)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            configurationService.getAsString(missingKey);
        });

        assertEquals("Occurred an error while getting configuration as string with key:" + missingKey, exception.getMessage());
    }
}