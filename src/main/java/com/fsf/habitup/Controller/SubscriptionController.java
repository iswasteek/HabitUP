package com.fsf.habitup.Controller;

import com.fsf.habitup.Enums.SubscriptionType;
import com.fsf.habitup.Service.SubscriptionService;
import com.fsf.habitup.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habit/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Endpoint to get a subscription by user ID
    @PreAuthorize("hasAuthority('VIEW_SUBSCRIPTIONS')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Subscription> getSubscriptionByUser(@PathVariable Long userId) {
        Optional<Subscription> subscription = subscriptionService.getSubscriptionByUser(userId);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all subscriptions by type (e.g., FREE, PREMIUM)
    @PreAuthorize("hasAuthority('VIEW_SUBSCRIPTIONS')")
    @GetMapping("/type/{subscriptionType}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByType(@PathVariable SubscriptionType subscriptionType) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByType(subscriptionType);
        return ResponseEntity.ok(subscriptions);
    }

    // Endpoint to create or update a subscription
    @PreAuthorize("hasAuthority('MANAGE_SUBSCRIPTIONS')")
    @PostMapping("/create-or-update")
    public ResponseEntity<Subscription> createOrUpdateSubscription(@RequestBody Subscription subscription) {
        Subscription savedSubscription = subscriptionService.createOrUpdateSubscription(subscription);
        return ResponseEntity.ok(savedSubscription);
    }

    // Endpoint to delete a subscription by subscription ID
    @PreAuthorize("hasAuthority('MANAGE_SUBSCRIPTIONS')")
    @DeleteMapping("/delete/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to fetch all subscriptions
    @PreAuthorize("hasAuthority('VIEW_SUBSCRIPTIONS')")
    @GetMapping("/get-all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    // Endpoint to check if a user has an active subscription
    @PreAuthorize("hasAuthority('VIEW_SUBSCRIPTIONS')")
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<Boolean> hasActiveSubscription(@PathVariable Long userId) {
        boolean hasActive = subscriptionService.hasActiveSubscription(userId);
        return ResponseEntity.ok(hasActive);
    }

    // Endpoint to extend the subscription by adding extra time
    @PreAuthorize("hasAuthority('MANAGE_SUBSCRIPTIONS')")
    @PutMapping("/extend/{subscriptionId}")
    public ResponseEntity<Subscription> extendSubscription(@PathVariable Long subscriptionId, @RequestParam int additionalDays) {
        try {
            Subscription extendedSubscription = subscriptionService.extendSubscription(subscriptionId, additionalDays);
            return ResponseEntity.ok(extendedSubscription);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
