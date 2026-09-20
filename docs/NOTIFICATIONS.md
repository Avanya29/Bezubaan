# Notification System

> [!NOTE]
> **Repository State Notice**: As of current verification, the Bezubaan repository is empty (containing only a placeholder `README.md`). There is no notification infrastructure, service implementation, client configuration, template engine, or notification pipeline.

---

## 1. Current State

| Dimension | Current Verification Status |
| :--- | :--- |
| **Notification Service** | None exists. No backend modules, endpoints, or delivery workers are implemented. |
| **FCM Configuration** | `Not set up`. No Firebase Admin SDK, service account credentials, or project initialization exists. |
| **Notification Templates** | `None exist`. No email, SMS, push, or in-app templates or localization assets exist. |
| **User Preference Tracking** | None exists. No schema or persistence for opt-in/opt-out or channel preferences. |
| **Repository Baseline** | Empty repository with zero application code. |

---

## 2. Intended Technology

> [!IMPORTANT]
> The notification technology listed below originates from the initial project brief. It is **INTENDED** and **NOT confirmed as needed**. Its suitability, pricing tiers, platform support, and delivery guarantees must be validated before adoption.

| Technology | Intended Role | Confirmation Status |
| :--- | :--- | :--- |
| **Firebase Cloud Messaging (FCM)** | Mobile and web push notification dispatch | `INTENDED — NOT CONFIRMED AS NEEDED` |

---

## 3. Notification Decisions & Unknowns

The core functional and architectural aspects of the notification system require decisions and product input:

| Dimension | Status | Notes & Options |
| :--- | :--- | :--- |
| **Notification Channels** | `UNKNOWN — REQUIRES DECISION` | Options to evaluate: Push notifications (FCM), Email (e.g., SendGrid, AWS SES, Resend), SMS (e.g., Twilio, AWS SNS), In-App notification feed |
| **Notification Types & Triggers** | `UNKNOWN — REQUIRES PRODUCT REQUIREMENTS` | Specific alert categories (e.g., rescue alert dispatch, volunteer response, status updates, system alerts) depend on finalized product workflows |
| **User Preference Management** | `UNKNOWN — REQUIRES DECISION` | Granular user channel preferences, quiet hours/DND, category subscriptions, frequency capping |
| **Delivery Guarantees & Tracking** | `UNKNOWN — REQUIRES DECISION` | Delivery acknowledgement, open/read tracking, fallback channels on failure, at-least-once dispatch guarantees |
| **Template Management Strategy** | `UNKNOWN — REQUIRES DECISION` | Hardcoded templates vs. template engine (Handlebars, MJML) vs. third-party hosted template management |

---

## 4. Agent Resumption Guide

For future development agents implementing notification functionality:

1. **Obtain Product Requirements**: Do not create notification schemas or dispatch triggers until product requirements define the notification types.
2. **Confirm Channels & Providers**: Verify if FCM is accompanied or replaced by email or SMS providers.
3. **Set Up Secure Credentials**: Ensure Firebase service account keys and provider API tokens are injected securely via environment secrets, never committed to git.
4. **Decouple Dispatch via Events**: Coordinate with the event system (`docs/EVENTS.md`) to handle notification dispatch asynchronously off critical paths.
