package io.samtech.repository.rdb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.Authority;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Set<Authority> findAllByIdInAndStatus(final Collection<Long> ids, final Integer status);

    Set<Authority> findAllByFeatureId(final Long featureId);

    default Set<Authority> findAllActiveByIdIn(final Collection<Long> ids) {
        return findAllByIdInAndStatus(ids, CommonConstants.EntityStatus.ACTIVE);
    }

    Set<Authority> findAllByStatus(final Integer status);

    default Set<Authority> findAllActive() {
        return findAllByStatus(CommonConstants.EntityStatus.ACTIVE);
    }
}
