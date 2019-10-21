package com.didispace.scca.rest.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Anoyi on 2018/10/24.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public interface PermissionRepo extends JpaRepository<Permission, String> {

    List<Permission> findAllByUserId(String userId, Sort sort);

    void deleteAllByUserId(String userId);

    Permission findByUserIdAndEnvIdAndProjectId(String userId, String envId, String projectId);

}
