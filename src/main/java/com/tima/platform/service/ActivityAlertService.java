package com.tima.platform.service;

import com.tima.platform.converter.ActivityAlertConverter;
import com.tima.platform.exception.AppException;
import com.tima.platform.model.api.AppResponse;
import com.tima.platform.model.api.response.ActivityAlertRecord;
import com.tima.platform.model.constant.AlertStatus;
import com.tima.platform.model.constant.AlertType;
import com.tima.platform.model.constant.StatusType;
import com.tima.platform.repository.ActivityAlertRepository;
import com.tima.platform.util.AppError;
import com.tima.platform.util.LoggerHelper;
import com.tima.platform.util.ReportSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.tima.platform.exception.ApiErrorHandler.handleOnErrorResume;
import static com.tima.platform.util.AppUtil.buildAppResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Service
@RequiredArgsConstructor
public class ActivityAlertService {
    private final LoggerHelper log = LoggerHelper.newInstance(ActivityAlertService.class.getName());
    private final ActivityAlertRepository alertRepository;

    private static final String ALERT_MSG = "Alert request executed successfully";
    private static final String INVALID_USER = "The user is invalid";

    public Mono<AppResponse> addNotice(ActivityAlertRecord activityAlertRecord) {
        log.info("Adding alert to ", activityAlertRecord.userPublicId());
        return alertRepository.save(ActivityAlertConverter.mapToEntity(activityAlertRecord))
                .map(ActivityAlertConverter::mapToRecord)
                .map(alertRecord -> buildAppResponse(alertRecord, ALERT_MSG))
                .onErrorResume(t ->
                        handleOnErrorResume(new AppException(AppError.massage(t.getMessage())), BAD_REQUEST.value()));
    }

    public Mono<AppResponse> getAllAlerts(String publicId, ReportSettings settings) {
        log.info("Getting User alerts for ", publicId);
        return alertRepository.findByUserPublicId(publicId, setPage(settings))
                .collectList()
                .map(ActivityAlertConverter::mapToRecords)
                .map(alertRecord -> buildAppResponse(alertRecord, ALERT_MSG))
                .switchIfEmpty(handleOnErrorResume(new AppException(INVALID_USER), BAD_REQUEST.value()));

    }

    public Mono<AppResponse> getUserAlerts(String publicId,
                                           String type,
                                           String status,
                                           String statusType,
                                           ReportSettings settings) {
        log.info("Getting User alerts for ", publicId, " for ", type);
        return alertRepository.findByUserPublicIdAndTypeAndTypeStatusAndStatus(
                publicId,
                type,
                statusType,
                status,
                setPage(settings))
                .collectList()
                .map(ActivityAlertConverter::mapToRecords)
                .map(alertRecord -> buildAppResponse(alertRecord, ALERT_MSG))
                .switchIfEmpty(handleOnErrorResume(new AppException(INVALID_USER), BAD_REQUEST.value()));

    }

    public Mono<AppResponse> getEnums(String type) {
        if(type.equalsIgnoreCase("ALERT_TYPE")) return getAllTypes();
        else if(type.equalsIgnoreCase("ALERT_STATUS")) return getAllStatus();
        else return getAllTypeStatus();
    }

    public Mono<AppResponse> getEnumTypes() {
        return Mono.just(buildAppResponse(List.of("ALERT_TYPE", "ALERT_STATUS", "ALERT_STATUS_TYPE"), ALERT_MSG));
    }

    public Mono<AppResponse> getAllTypes() {
        return Mono.just(buildAppResponse(AlertType.values(), ALERT_MSG));
    }

    public Mono<AppResponse> getAllStatus() {
        return Mono.just(buildAppResponse(AlertStatus.values(), ALERT_MSG));
    }

    public Mono<AppResponse> getAllTypeStatus() {
        return Mono.just(buildAppResponse(StatusType.values(), ALERT_MSG));
    }

    private Pageable setPage(ReportSettings settings) {
        return PageRequest.of(settings.getPage(), settings.getSize(),
                Sort.Direction.fromString(settings.getSortIn()), settings.getSortBy());
    }
}
