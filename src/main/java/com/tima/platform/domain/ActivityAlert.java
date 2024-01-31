package com.tima.platform.domain;

import com.tima.platform.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("public.activity_alert")
public class ActivityAlert implements Serializable, Persistable<Integer> {

    @Id
    private Integer id;
    private String userPublicId;
    private String title;
    private String message;
    private String type;
    private String typeStatus;
    private String status;
    private Instant createdOn;
    private Instant viewedOn;

    @Override
    public boolean isNew() {
        boolean newRecord = AppUtil.isNewRecord(id);
        if(newRecord) {
            createdOn = Instant.now();
        }
        return newRecord;
    }
}
