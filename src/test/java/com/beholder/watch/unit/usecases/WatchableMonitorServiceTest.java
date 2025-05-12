package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.adapters.inbound.dtos.HttpResponseDetailsDto;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.model.watchable.WatchableStatus;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.micrometer.core.instrument.Counter;

import com.beholder.watch.adapters.inbound.dtos.HttpResponseDetailsDto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WatchableMonitorServiceTest {

    private static final String WATCHABLE_NAME = "TEST_SERVICE";
    private static final int RESPONSE_STATUS_OK = 200;
    private static final int RESPONSE_TIME = 123;
    private static final String ERROR_MESSAGE = "Internal Server Error";

    private MeterRegistry meterRegistry;

    @InjectMocks
    private WatchableMonitorService watchableMonitorService;

    @BeforeEach
    public void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        watchableMonitorService = new WatchableMonitorService(meterRegistry);
    }

    @Test
    public void shouldUpdateMetricsSuccessfully() {
        HttpResponseDetailsDto httpResponseDetails = HttpResponseDetailsDto
            .builder()
            .responseTime(RESPONSE_TIME)
            .watchableStatus(WatchableStatus.UP)
            .responseStatus(RESPONSE_STATUS_OK)
            .errorMessage(ERROR_MESSAGE)
            .build();

        watchableMonitorService.updateMetrics(WATCHABLE_NAME, httpResponseDetails);

        Gauge serviceStatusGauge = meterRegistry.find("service_status").tag("service", WATCHABLE_NAME).gauge();
        Gauge responseStatusGauge = meterRegistry.find("service_response_status").tag("service", WATCHABLE_NAME).gauge();
        Counter requestCounter = meterRegistry.find("service_requests_total").tag("service", WATCHABLE_NAME).counter();
        Timer responseTimer = meterRegistry.find("service_response_time").tag("service", WATCHABLE_NAME).timer();
        Counter errorCounter = meterRegistry.find("service_error_count").tag("service", WATCHABLE_NAME).tag("error_message", ERROR_MESSAGE).counter();

        assertNotNull(serviceStatusGauge);
        assertEquals(1.0, serviceStatusGauge.value());

        assertNotNull(responseStatusGauge);
        assertEquals(RESPONSE_STATUS_OK, responseStatusGauge.value());

        assertNotNull(requestCounter);
        assertEquals(1.0, requestCounter.count());

        assertNotNull(responseTimer);
        assertEquals(1, responseTimer.count());
        assertEquals(RESPONSE_TIME, responseTimer.totalTime(TimeUnit.MILLISECONDS));

        assertNotNull(errorCounter);
        assertEquals(1.0, errorCounter.count());
    }

    @Test
    public void shouldTruncateErrorMessageIfTooLong() {
        String longErrorMessage = new String(new char[150]).replace('\0', 'a');
        HttpResponseDetails httpResponseDetails = HttpResponseDetailsDto.builder()
            .responseTime(RESPONSE_TIME)
            .watchableStatus(WatchableStatus.UP)
            .responseStatus(RESPONSE_STATUS_OK)
            .errorMessage(longErrorMessage)
            .build();
        
        watchableMonitorService.updateMetrics(WATCHABLE_NAME, httpResponseDetails);

        Counter errorCounter = meterRegistry.find("service_error_count")
            .tag("service", WATCHABLE_NAME)
            .tag("error_message", longErrorMessage.substring(0, 100) + "...")
            .counter();

        assertNotNull(errorCounter);
        assertEquals(1.0, errorCounter.count());
    }

    @Test
    public void shouldNotRecordErrorMessageIfEmpty() {
        HttpResponseDetails httpResponseDetails = HttpResponseDetailsDto.builder()
            .responseTime(RESPONSE_TIME)
            .watchableStatus(WatchableStatus.UP)
            .responseStatus(RESPONSE_STATUS_OK)
            .errorMessage("")
            .build();

        watchableMonitorService.updateMetrics(WATCHABLE_NAME, httpResponseDetails);

        Counter errorCounter = meterRegistry.find("service_error_count").tag("service", WATCHABLE_NAME).counter();

        assertNull(errorCounter);
    }
}
