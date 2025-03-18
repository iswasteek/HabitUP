package com.fsf.habitup.Enums;

public enum PermissionType {

    VIEW_USERS, // Permission to view user details
    MANAGE_USERS, // Permission to create, update, delete users
    ACTIVATE_USERS, // Permission to activate/deactivate user accounts
    LOCK_UNLOCK_USERS, // Permission to lock or unlock user accounts
    RESET_USER_PASSWORDS, // Permission to reset passwords of users

    // Doctor Management
    VIEW_DOCTORS, // Permission to view doctor details
    MANAGE_DOCTORS, // Permission to approve, reject, update doctor profiles
    VERIFY_DOCTORS, // Permission to verify doctor credentials
    ACTIVATE_DOCTORS, // Permission to activate/deactivate doctor accounts

    // Document Verification
    VIEW_DOCUMENTS, // Permission to view uploaded documents
    VERIFY_DOCUMENTS, // Permission to approve or reject documents
    MANAGE_DOCUMENTS, // Permission to delete or modify document records

    // Permission Control
    VIEW_PERMISSIONS, // Permission to view user/doctor permissions
    MANAGE_PERMISSIONS, // Permission to assign or revoke permissions

    // System Monitoring
    VIEW_LOGS, // Permission to access system logs
    VIEW_ERROR_LOGS, // Permission to access error logs
    MONITOR_SYSTEM, // Permission to monitor system health
    VIEW_AUDIT_TRAILS, // Permission to track user/admin activities

    // System Administration
    MANAGE_SETTINGS, // Permission to update global system settings
    SEND_NOTIFICATIONS, // Permission to send system-wide notifications
    MANAGE_NOTIFICATIONS, // Permission to control notification settings
    MANAGE_ROLES, // Permission to create or modify user roles
    CONFIGURE_SECURITY, // Permission to update security settings

    // Financial & Subscription Management (If applicable)
    VIEW_SUBSCRIPTIONS, // Permission to view user subscription details
    MANAGE_SUBSCRIPTIONS, // Permission to update subscription plans
    HANDLE_PAYMENTS, // Permission to process refunds or handle payments

    // Miscellaneous
    ACCESS_API, // Permission to interact with external APIs
    EXPORT_DATA, // Permission to export user/system data
    IMPORT_DATA, // Permission to import external data
    CUSTOMIZE_DASHBOARD // Permission to modify dashboard layout/settings

}
