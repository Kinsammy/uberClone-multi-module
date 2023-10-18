//package io.samtech.repository.rdb;
//
//import io.samtech.constants.CommonConstants;
//import io.samtech.entity.rdb.Role;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.Collection;
//import java.util.Optional;
//import java.util.Set;
//
//public interface RoleRepository extends CrudRepository<Role, Long> {
//    boolean existsByName(final String name);
//
//    Optional<Role> findRoleByIdAndStatus(Long id, Integer status);
//
//    default Optional<Role> findActiveRoleById(Long id) {
//        return findRoleByIdAndStatus(id, CommonConstants.EntityStatus.ACTIVE);
//    }
//
//    Optional<Role> findRoleByNameAndStatus(String name, Integer status);
//
//    default Optional<Role> findActiveRoleByName(String name) {
//        return findRoleByNameAndStatus(name, CommonConstants.EntityStatus.ACTIVE);
//    }
//
//    Set<Role> findAllByIdInAndStatus(Collection<Long> id, Integer status);
//
//    default Set<Role> findAllActiveByIdIn(final Collection<Long> ids) {
//        return findAllByIdInAndStatus(ids, CommonConstants.EntityStatus.ACTIVE);
//    }
//
//}
