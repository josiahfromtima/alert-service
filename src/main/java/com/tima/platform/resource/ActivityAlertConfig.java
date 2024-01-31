package com.tima.platform.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Configuration
public class ActivityAlertConfig {
    public static final String API_V1_URL = "/v1";
    public static final String ALERT_BASE = API_V1_URL + "/notifications";
    public static final String GET_ALL_MY_ALERTS = ALERT_BASE;
    public static final String GET_MY_ALERTS = ALERT_BASE + "/type/{type}";
    public static final String GET_ENUMS = ALERT_BASE + "/enum/{enumType}";
    public static final String GET_ENUM_TYPE = ALERT_BASE + "/enums";

    @Bean
    public RouterFunction<ServerResponse> alertEndpointHandler(ActivityAlertHandler handler) {
        return route()
                .GET(GET_ALL_MY_ALERTS, accept(MediaType.APPLICATION_JSON), handler::getAllAlertsForUser)
                .GET(GET_MY_ALERTS, accept(MediaType.APPLICATION_JSON), handler::getMyAlerts)
                .GET(GET_ENUMS, accept(MediaType.APPLICATION_JSON), handler::getTypes)
                .GET(GET_ENUM_TYPE, accept(MediaType.APPLICATION_JSON), handler::getEnumTypes)
                .build();
    }
}
