package com.didispace.scca.rest.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findById(String id);

    User findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
