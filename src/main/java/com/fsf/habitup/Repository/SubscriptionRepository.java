package com.fsf.habitup.Repository;

import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUser_UserId(Long userId);

    // Custom query method to find subscriptions by type (e.g., FREE, PREMIUM)
    List<Subscription> findBySubscriptionType(SubscriptionType subscriptionType);
}
