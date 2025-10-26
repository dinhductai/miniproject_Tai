package com.microsv.user_service.repository;

import com.microsv.user_service.entity.User;
import com.microsv.user_service.repository.query.UserQuery;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query(value = UserQuery.SEARCH_USER_BY_NAME_OR_EMAIL,nativeQuery = true)
    List<Tuple> searchUsersByNameOrEmail(@Param("keyword") String keyword);
    List<User> searchUsersByUserName(String name);
}
