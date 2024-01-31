package com.tima.platform.event;

import com.tima.platform.model.api.response.ActivityAlertRecord;
import com.tima.platform.service.ActivityAlertService;
import com.tima.platform.util.LoggerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Configuration
@RequiredArgsConstructor
public class ActivityAlertEvent {
    private final LoggerHelper log = LoggerHelper.newInstance(ActivityAlertEvent.class.getName());
    private final ActivityAlertService alertService;

    @Bean
    public Consumer<ActivityAlertRecord> alert() {
        return request -> {
            log.info("User Activity Alert--- ", request);
            if(request == null) return;
            alertService.addNotice(request)
                    .subscribe(r -> log.info("Saved Alert: ", r));
        };
    }

}
