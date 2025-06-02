package com.restaurantreview.user_service.repository;

import com.restaurantreview.user_service.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
  Optional<UserAccount> findByEmail(String email);
}
