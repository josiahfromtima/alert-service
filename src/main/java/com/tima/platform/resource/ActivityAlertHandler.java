package com.tima.platform.resource;

import com.tima.platform.config.AuthTokenConfig;
import com.tima.platform.model.api.ApiResponse;
import com.tima.platform.service.ActivityAlertService;
import com.tima.platform.util.LoggerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.tima.platform.model.api.ApiResponse.buildServerResponse;
import static com.tima.platform.model.api.ApiResponse.reportSettings;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Service
@RequiredArgsConstructor
public class ActivityAlertHandler {
    LoggerHelper log = LoggerHelper.newInstance(ActivityAlertHandler.class.getName());
    private final ActivityAlertService alertService;
    private static final String X_FORWARD_FOR = "X-Forwarded-For";

    /**
     *  This section marks the system country activities
     */
    public Mono<ServerResponse> getAllAlertsForUser(ServerRequest request)  {
        Mono<JwtAuthenticationToken> jwtAuthToken = AuthTokenConfig.authenticatedToken(request);
        log.info("Get All Alert for User Requested", request.headers().firstHeader(X_FORWARD_FOR));
        return jwtAuthToken
                .map(ApiResponse::getPublicIdFromToken)
                .map(id ->  alertService.getAllAlerts(id, reportSettings(request, false)) )
                .flatMap(ApiResponse::buildServerResponse);
    }

    public Mono<ServerResponse> getMyAlerts(ServerRequest request)  {
        Mono<JwtAuthenticationToken> jwtAuthToken = AuthTokenConfig.authenticatedToken(request);
        String type = request.pathVariable("type");
        String status = request.queryParam("status").orElse("");
        String statusType = request.queryParam("typeStatus").orElse("");
        log.info("Get User Alerts by filters Requested", request.headers().firstHeader(X_FORWARD_FOR));
        return jwtAuthToken
                .map(ApiResponse::getPublicIdFromToken)
                .map(id ->  alertService.getUserAlerts(id,
                        type,
                        status,
                        statusType,
                        reportSettings(request, false)) )
                .flatMap(ApiResponse::buildServerResponse);
    }

    public Mono<ServerResponse> getEnumTypes(ServerRequest request)  {
        log.info("Get Alert Enum Types Requested", request.headers().firstHeader(X_FORWARD_FOR));
        return buildServerResponse(alertService.getEnumTypes());
    }

    public Mono<ServerResponse> getTypes(ServerRequest request)  {
        String enumType = request.pathVariable("enumType");
        log.info("Get Alert Enum Type Requested", request.headers().firstHeader(X_FORWARD_FOR));
        return buildServerResponse(alertService.getEnums(enumType));
    }
}
