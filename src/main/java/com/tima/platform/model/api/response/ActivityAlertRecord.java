package com.tima.platform.model.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

import java.time.Instant;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ActivityAlertRecord(String userPublicId,
                                  String title,
                                  String message,
                                  String type,
                                  String typeStatus,
                                  String status,
                                  Instant createdOn,
                                  Instant viewedOn) {}