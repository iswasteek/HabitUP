package com.fsf.habitup.Service;

import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Repository.SubscriptionRepository;
import com.fsf.habitup.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // Method to get a subscription by user ID
    public Optional<Subscription> getSubscriptionByUser(Long userId) {
        return subscriptionRepository.findByUser_UserId(userId);
    }

    // Method to get all subscriptions by type (e.g., FREE, PREMIUM)
    public List<Subscription> getSubscriptionsByType(SubscriptionType subscriptionType) {
        return subscriptionRepository.findBySubscriptionType(subscriptionType);
    }

    // Method to create or update a subscription
    public Subscription createOrUpdateSubscription(Subscription subscription) {
        // Check if subscription exists by ID, and update if necessary
        if (subscription.getSubscription_Id() != null && subscriptionRepository.existsById(subscription.getSubscription_Id())) {
            // Existing subscription update logic can go here
        }
        return subscriptionRepository.save(subscription);  // Save new or updated subscription
    }

    // Method to delete a subscription by subscription ID
    public void deleteSubscription(Long subscriptionId) {
        // You can add additional logic here, such as checking if the subscription exists
        subscriptionRepository.deleteById(subscriptionId);
    }

    // Method to fetch all subscriptions (optional)
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // Method to check if a user has an active subscription
    public boolean hasActiveSubscription(Long userId) {
        Optional<Subscription> subscription = getSubscriptionByUser(userId);
        return subscription.isPresent() && "ACTIVE".equals(subscription.get().getStatus());
    }

    // Method to extend the subscription by adding extra time
    public Subscription extendSubscription(Long subscriptionId, int additionalDays) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isPresent()) {
            Subscription subscription = subscriptionOpt.get();
            // Extend the end date by the specified number of days (assuming you have a method for that)
            // subscription.setEndDate(newEndDate);
            return subscriptionRepository.save(subscription);
        } else {
            throw new RuntimeException("Subscription not found");
        }
    }
}
