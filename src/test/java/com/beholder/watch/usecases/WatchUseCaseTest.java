import com.beholder.watch.adapters.outbound.application.services.HttpService;
import com.beholder.watch.adapters.outbound.application.services.JpaLogService;
import com.beholder.watch.adapters.outbound.application.services.JpaWatchableService;
import com.beholder.watch.adapters.outbound.application.services.TaskManagerService;
import com.beholder.watch.adapters.outbound.application.services.WatchService;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.model.Log;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.beholder.watch.adapters.inbound.dtos.HttpResponseDetailsDto;
import com.beholder.watch.adapters.outbound.application.services.ScheduleService;
import com.beholder.watch.adapters.outbound.application.services.WatchableMonitorService;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class WatchSUseCaseTest {

    @Mock
    private JpaLogService logService;

    @Mock
    private HttpService httpService;

    @Mock
    private JpaWatchableService watchableService;

    @Mock
    private WatchableMonitorService watchableMonitorService;

    @Mock
    private TaskManagerService taskManager;

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private WatchService watchService;

    private Watchable MOCK_WATCHABLE = new Watchable();

    private HttpResponseDetails MOCK_RESPONSE = HttpResponseDetailsDto.builder()
    .responseStatus(HttpStatus.OK.value())
    .responseTime(200L)
    .errorMessage(null)
    .build();

    @BeforeEach
    void setup() {
        MOCK_WATCHABLE.setId(1L);
        MOCK_WATCHABLE.setName("Test Watchable");
        MOCK_WATCHABLE.setUrl("http://test.com");
        MOCK_WATCHABLE.setCheckInterval(30);
    }

    @Test
    void shouldStartMonitoringWhenInitialized() {
        when(watchableService.findByPage(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        watchService.start();

        verify(watchableService, times(1)).findByPage(anyInt(), anyInt());
    }

    @Test
    void shouldWatchAndUpdateMetrics() {
        when(httpService.getRequest(MOCK_WATCHABLE.getUrl())).thenReturn(MOCK_RESPONSE);

        watchService.watch(MOCK_WATCHABLE);

        verify(httpService, times(1)).getRequest(MOCK_WATCHABLE.getUrl());
        verify(watchableService, times(1)).updateWatchableStatus(eq(1L), eq(WatchableStatus.UP));
        verify(logService, times(1)).save(any(Log.class));
        verify(watchableMonitorService, times(1)).updateMetrics(eq("Test Watchable"), eq(MOCK_RESPONSE));
    }
}
