package io.samtech.serviceImpl.role;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.Authority;
import io.samtech.entity.rdb.Role;
import io.samtech.exception.RoleNotFoundException;
import io.samtech.repository.rdb.AuthorityRepository;
import io.samtech.repository.rdb.RoleRepository;
import io.samtech.serviceApi.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    public final AuthorityRepository authorityRepository;
    @Override
    public Set<Role> findAllActiveByIds(Set<Long> ids) {
        Role admin = findAdminRole();
        if (ids.contains(admin.getId())) {
            return Set.of(admin);
        }
        Set<Role> roles = roleRepository.findAllActiveByIdIn(ids);
        roles.forEach (role -> {
            Set<Authority> authorities = authorityRepository.findAllActiveByIdIn(role.authorityIds());
            role.setAuthorities(authorities);
        });
        return roles;
    }

    @Override
    public Role findAdminRole() {
        Role admin = roleRepository.findActiveRoleByName(CommonConstants.Role.DEFAULT_ROLE_ADMIN)
                .orElseThrow(RoleNotFoundException::new);
        Set<Authority> authorities = authorityRepository.findAllActive();
        admin.setAuthorities(authorities);
        return admin;
    }

    @Override
    public Role findActiveRoleByName(String name) {
        return roleRepository.findActiveRoleByName(name)
                .orElseThrow(RoleNotFoundException::new);
    }

    @Override
    public Role findActiveRoleById(Long id) {
        return roleRepository.findActiveRoleById(id)
                .orElseThrow(RoleNotFoundException::new);
    }
}
