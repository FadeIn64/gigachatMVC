package com.gigachatmvc.repos;

import com.gigachatmvc.entities.classes.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}
