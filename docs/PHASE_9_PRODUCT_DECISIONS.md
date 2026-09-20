# Phase 9: Community + Donations + Notifications — Product Decisions

> **Status**: ✅ CONFIRMED

This document tracks the official Product Owner decisions for implementing the Community, Donations, Campaigns, and Notifications module (Phase 9).

## A. COMMUNITY

**1. Posts & Rescue Stories**
- Users may create free-form community posts.
- Rescue stories are supported as a distinct post/story type (manual creation by authorized users, not automatic).
- MVP capabilities: Create post/story, view feed, view single post/story, edit own content (within 30 mins), delete own content.

**2. Interactions**
- MVP Interactions: Likes, Comments, Saves, Shares (via link), Follows.
- All interactions strictly enforce auth and ownership. No anonymous interactions.

**3. Lost/Found Portal**
- Lost/Found is a **DISTINCT DOMAIN ENTITY**, not a standard community post.
- Lifecycle: `LOST -> SEARCHING -> FOUND -> REUNITED -> CLOSED` or `LOST -> SEARCHING -> CLOSED` or `FOUND -> REUNITED -> CLOSED`.
- Invalid state transitions must be rejected.

**4. Moderation & Reporting**
- Authenticated users can report community content.
- Report fields: `reporterId`, target content, reason/category, optional description, status, timestamps.
- Report states: `PENDING`, `REVIEWED`, `RESOLVED`, `DISMISSED`.
- Content deletion: Users can delete their own. `NGO_ADMIN` and `ADMIN` can moderate/hide/delete. Moderation is human-controlled.

**5. Privacy**
- MVP Privacy options: `PUBLIC` (visible to authenticated users), `PRIVATE` (owner + admins).
- Default: `PUBLIC`.
- No `FRIENDS_ONLY` mode.

**15. Content Ownership / Editing**
- 30-minute edit window for users. After 30 minutes, users cannot edit, but Admins can moderate.
- Users can delete their own content unless under moderation/legal hold.
- Soft-deletion applies to community content for audit history.

## B. DONATIONS & CAMPAIGNS

**6. Payment Provider**
- Gateway: **RAZORPAY**.
- Must use a provider abstraction/service. Webhook signatures must be verified. Secrets in env only.

**7. Campaigns vs General Donations**
- Both are supported. Campaigns are first-class entities.
- Campaigns may reference `RescueCase` or `Animal`.
- A donation stores the campaign reference when applicable. General donations have none.

**8. Anonymity & PII**
- Anonymous donations are supported.
- Distinguish: `donorId` (nullable), `isAnonymous`, `amount`, `currency`, `paymentStatus`, `providerRef`, timestamps.
- Public views hide identity. Admin views retain required info for tax/reconciliation.

**9. Currency & Amounts**
- MVP currency: **INR only**.
- Store in integer minor units (paise). Minimum: ₹10.
- Presets: ₹100, ₹250, ₹500, ₹1000, ₹2500, ₹5000. Custom >= ₹10 allowed.
- Validated server-side.

**10. Reconciliation & Idempotency**
- Payment states: `PENDING`, `SUCCESS`, `FAILED`, `REFUNDED`.
- Transitions: `PENDING -> SUCCESS`, `PENDING -> FAILED`, `SUCCESS -> REFUNDED`.
- Idempotent webhook processing. Never mark successful solely from client-side redirect.

**16. Payment Security**
- NEVER store raw payment cards, CVV, or bank credentials.
- Store Razorpay order/payment reference, status, amount, currency, webhook identifiers.

## C. NOTIFICATIONS

**11. Channels**
- MVP Channels: **In-App**, **Push**, **Email** (for important). No SMS.

**12. Triggers**
- Explicit events configured for Rescue, Medical, Adoption, Foster, Community, Donations, and Proximity.
- Auth enforced (do not send to unauthorized users).

**13. Delivery Mechanism**
- Use **RabbitMQ** for asynchronous notification routing.
- Flow: NestJS business operation -> Domain Event -> RabbitMQ -> Notifications Consumer -> Dispatcher -> Channel.
- Consumer must be idempotent. Failed non-critical delivery must not roll back the primary transaction.

**14. Preferences**
- Users can opt-out of non-critical categories (Community, Rescue, Adoption, Foster, Donations/Campaigns, Proximity).
- Mandatory security/transactional notifications cannot be disabled. Default: Enabled.
