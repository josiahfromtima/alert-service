package com.tima.platform.converter;

import com.tima.platform.domain.ActivityAlert;
import com.tima.platform.model.api.response.ActivityAlertRecord;

import java.util.List;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
public class ActivityAlertConverter {
    private ActivityAlertConverter() {}

    public static synchronized ActivityAlert mapToEntity(ActivityAlertRecord dto) {
        return ActivityAlert.builder()
                .userPublicId(dto.userPublicId())
                .title(dto.title())
                .message(dto.message())
                .type(dto.type())
                .status(dto.status())
                .typeStatus(dto.typeStatus())
                .build();
    }

    public static synchronized ActivityAlertRecord mapToRecord(ActivityAlert entity) {
        return  ActivityAlertRecord.builder()
                .title(entity.getTitle())
                .message(entity.getMessage())
                .type(entity.getType())
                .status(entity.getStatus())
                .typeStatus(entity.getTypeStatus())
                .createdOn(entity.getCreatedOn())
                .viewedOn(entity.getViewedOn())
                .build();
    }

    public static synchronized List<ActivityAlertRecord> mapToRecords(List<ActivityAlert> entities) {
        return entities
                .stream()
                .map(ActivityAlertConverter::mapToRecord)
                .toList();
    }
    public static synchronized List<ActivityAlert> mapToEntities(List<ActivityAlertRecord> records) {
        return records
                .stream()
                .map(ActivityAlertConverter::mapToEntity)
                .toList();
    }

}
