package com.microsv.user_service.repository;

import com.microsv.user_service.entity.User;
import com.microsv.user_service.repository.query.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUserId(Long userId);
    long count();

    @Query(value = UserQuery.COUNT_ALL_NEW_USERS_THIS_WEEK,nativeQuery = true)
    Long countUsersRegisteredThisWeek();

}
