const fs = require('fs');
let schema = fs.readFileSync('prisma/schema.prisma', 'utf8');

const additional = `
// -------------------------------------------
// Phase 9 Enums
// -------------------------------------------
enum PostType {
  POST
  STORY
}

enum PostPrivacy {
  PUBLIC
  PRIVATE
}

enum ModerationStatus {
  PENDING
  REVIEWED
  RESOLVED
  DISMISSED
}

enum LostFoundStatus {
  LOST
  SEARCHING
  FOUND
  REUNITED
  CLOSED
}

enum PaymentStatus {
  PENDING
  SUCCESS
  FAILED
  REFUNDED
}

enum NotificationChannel {
  IN_APP
  PUSH
  EMAIL
}

enum NotificationCategory {
  RESCUE
  MEDICAL
  ADOPTION
  FOSTER
  COMMUNITY
  DONATION
  PROXIMITY
  SECURITY
}

// -------------------------------------------
// Phase 9 Models
// -------------------------------------------

model CommunityPost {
  id        String      @id @default(cuid())
  authorId  String
  author    User        @relation("CommunityPosts", fields: [authorId], references: [id], onDelete: Cascade)
  type      PostType    @default(POST)
  privacy   PostPrivacy @default(PUBLIC)
  content   String

  likes     PostLike[]
  comments  PostComment[]
  saves     PostSave[]

  createdAt DateTime    @default(now()) @map("created_at")
  updatedAt DateTime    @updatedAt @map("updated_at")
  deletedAt DateTime?   @map("deleted_at")

  @@map("community_posts")
}

model PostLike {
  userId String
  user   User   @relation("UserLikes", fields: [userId], references: [id], onDelete: Cascade)
  postId String
  post   CommunityPost @relation(fields: [postId], references: [id], onDelete: Cascade)

  createdAt DateTime @default(now()) @map("created_at")

  @@id([userId, postId])
  @@map("post_likes")
}

model PostComment {
  id        String        @id @default(cuid())
  postId    String
  post      CommunityPost @relation(fields: [postId], references: [id], onDelete: Cascade)
  authorId  String
  author    User          @relation("UserComments", fields: [authorId], references: [id], onDelete: Cascade)
  content   String

  createdAt DateTime  @default(now()) @map("created_at")
  updatedAt DateTime  @updatedAt @map("updated_at")
  deletedAt DateTime? @map("deleted_at")

  @@map("post_comments")
}

model PostSave {
  userId String
  user   User   @relation("UserSaves", fields: [userId], references: [id], onDelete: Cascade)
  postId String
  post   CommunityPost @relation(fields: [postId], references: [id], onDelete: Cascade)

  createdAt DateTime @default(now()) @map("created_at")

  @@id([userId, postId])
  @@map("post_saves")
}

model UserFollow {
  followerId  String
  follower    User @relation("Following", fields: [followerId], references: [id], onDelete: Cascade)
  followingId String
  following   User @relation("Followers", fields: [followingId], references: [id], onDelete: Cascade)

  createdAt DateTime @default(now()) @map("created_at")

  @@id([followerId, followingId])
  @@map("user_follows")
}

model ModerationReport {
  id          String           @id @default(cuid())
  reporterId  String
  reporter    User             @relation("UserReports", fields: [reporterId], references: [id], onDelete: Cascade)
  targetType  String           // e.g., "POST", "COMMENT"
  targetId    String
  reason      String
  description String?
  status      ModerationStatus @default(PENDING)

  createdAt DateTime @default(now()) @map("created_at")
  updatedAt DateTime @updatedAt @map("updated_at")

  @@map("moderation_reports")
}

model LostFoundIncident {
  id               String          @id @default(cuid())
  reporterId       String
  reporter         User            @relation("LostFoundIncidents", fields: [reporterId], references: [id], onDelete: Cascade)
  status           LostFoundStatus @default(LOST)
  animalType       String
  description      String
  lastSeenLocation String?

  createdAt DateTime @default(now()) @map("created_at")
  updatedAt DateTime @updatedAt @map("updated_at")

  @@map("lost_found_incidents")
}

model Campaign {
  id           String      @id @default(cuid())
  creatorId    String
  creator      User        @relation("Campaigns", fields: [creatorId], references: [id], onDelete: Cascade)
  title        String
  description  String
  goalAmount   Int?
  raisedAmount Int         @default(0)

  rescueCaseId String?
  rescueCase   RescueCase? @relation(fields: [rescueCaseId], references: [id])
  animalId     String?
  animal       Animal?     @relation(fields: [animalId], references: [id])

  donations    Donation[]

  createdAt DateTime @default(now()) @map("created_at")
  updatedAt DateTime @updatedAt @map("updated_at")

  @@map("campaigns")
}

model Donation {
  id                String        @id @default(cuid())
  donorId           String?
  donor             User?         @relation("Donations", fields: [donorId], references: [id], onDelete: SetNull)
  campaignId        String?
  campaign          Campaign?     @relation(fields: [campaignId], references: [id], onDelete: SetNull)
  isAnonymous       Boolean       @default(false)
  amount            Int           // in minor units (paise)
  currency          String        @default("INR")
  status            PaymentStatus @default(PENDING)
  providerOrderId   String?       @unique
  providerPaymentId String?       @unique

  createdAt DateTime @default(now()) @map("created_at")
  updatedAt DateTime @updatedAt @map("updated_at")

  @@map("donations")
}

model Notification {
  id       String               @id @default(cuid())
  userId   String
  user     User                 @relation("UserNotifications", fields: [userId], references: [id], onDelete: Cascade)
  category NotificationCategory
  channel  NotificationChannel
  title    String
  message  String
  payload  Json?
  isRead   Boolean              @default(false)

  createdAt DateTime @default(now()) @map("created_at")

  @@map("notifications")
}

model NotificationPreference {
  userId     String
  user       User                 @relation("UserPreferences", fields: [userId], references: [id], onDelete: Cascade)
  category   NotificationCategory
  isEnabled  Boolean              @default(true)

  @@id([userId, category])
  @@map("notification_preferences")
}
`;

if (!schema.includes('model CommunityPost {')) {
  schema = schema + '\n' + additional;

  // Insert RescueCase relations
  schema = schema.replace(
    /(model RescueCase \{[^}]*?)(\n\s*createdAt\s+DateTime)/s,
    '$1\n  campaigns      Campaign[]$2'
  );

  // Insert Animal relations
  schema = schema.replace(
    /(model Animal \{[^}]*?)(\n\s*createdAt\s+DateTime)/s,
    '$1\n  campaigns      Campaign[]$2'
  );

  fs.writeFileSync('prisma/schema.prisma', schema);
  console.log('Appended models successfully');
}
