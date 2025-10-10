package com.microsv.task_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "push_subscriptions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String endpoint;

    @Column(name = "p256dh", nullable = false)
    private String p256dh;

    @Column(nullable = false)
    private String auth;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Chỉ lưu ID
}