package com.tima.platform.repository;

import com.tima.platform.domain.ActivityAlert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
public interface ActivityAlertRepository extends ReactiveCrudRepository<ActivityAlert, Integer> {
    Flux<ActivityAlert> findByUserPublicId(String publicId, Pageable pageable);
    Flux<ActivityAlert> findByUserPublicIdAndTypeAndTypeStatusAndStatus(String publicId,
                                                                        String type,
                                                                        String typeStatus,
                                                                        String status,
                                                                        Pageable pageable);
}
