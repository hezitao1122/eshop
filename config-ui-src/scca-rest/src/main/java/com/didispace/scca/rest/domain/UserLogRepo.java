package com.didispace.scca.rest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Repository
public interface UserLogRepo extends JpaRepository<UserLog, String> {

    Page<UserLog> findAllByUsernameAndType(String username, int type, Pageable pageable);

}
