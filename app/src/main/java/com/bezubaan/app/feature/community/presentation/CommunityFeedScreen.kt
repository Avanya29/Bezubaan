package com.bezubaan.app.feature.community.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.bezubaan.app.feature.community.domain.model.Post

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CommunityFeedScreen(
    onNavigateToCreatePost: () -> Unit = {},
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    var selectedPostIdForComments by remember { mutableStateOf<String?>(null) }

    if (selectedPostIdForComments != null) {
        val postId = selectedPostIdForComments!!
        PostCommentsBottomSheet(
            comments = uiState.currentComments,
            isLoading = uiState.isLoadingComments,
            onDismiss = { selectedPostIdForComments = null },
            onSubmitComment = { content ->
                viewModel.createComment(postId, content)
            }
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadPosts()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF9F8))
    ) {
        CommunityTopHeader()

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    ImpactTicker()
                    Spacer(modifier = Modifier.height(16.dp))
                    MobilizationHeroBar(onNavigateToCreatePost)
                    Spacer(modifier = Modifier.height(16.dp))
                    SegmentSelectionPills()
                }
            }

            // Loading State
            if (uiState.isLoading && uiState.posts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = Color(0xFF0F3822),
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "LOADING COMMUNITY FEED...",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF414942)
                            )
                        }
                    }
                }
            }

            // Error State
            if (uiState.error != null && uiState.posts.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .shadow(4.dp, RoundedCornerShape(2.dp))
                            .background(Color(0xFFFFDAD6), RoundedCornerShape(2.dp))
                            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "FEED UNAVAILABLE",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF93000A)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            uiState.error ?: "Could not connect to the server.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF93000A),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadPosts() },
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color.Black),
                            modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                        ) {
                            Text("RETRY", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // Empty State
            if (!uiState.isLoading && uiState.error == null && uiState.posts.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .shadow(4.dp, RoundedCornerShape(2.dp))
                            .background(Color(0xFFF4EFE6), RoundedCornerShape(2.dp))
                            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, tint = Color(0xFF0F3822), modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "NO STORIES YET",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1C1B1B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Be the first to share a rescue story and inspire the community!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF414942),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onNavigateToCreatePost,
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color.Black),
                            modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                        ) {
                            Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("POST FIRST STORY", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // Real Posts from Backend
            items(uiState.posts, key = { it.id }) { post ->
                FeedPostCard(
                    post = post,
                    onLikeClick = { viewModel.toggleLike(post.id) },
                    onCommentClick = {
                        selectedPostIdForComments = post.id
                        viewModel.fetchComments(post.id)
                    }
                )
            }

            item {
                BottomCallToAction(onNavigateToCreatePost)
            }
        }
    }
}

// ===================== REUSABLE POST CARD =====================

@Composable
private fun FeedPostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFBF8F1), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
    ) {
        // Post Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EFE6))
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                        .shadow(2.dp, RoundedCornerShape(2.dp))
                        .background(Color(0xFFE2F3E8), RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        post.authorName.take(1).uppercase(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F3822)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            post.authorName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF002210),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        formatTimestamp(post.createdAt),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF414942)
                    )
                }
            }
            // Post type badge
            Box(
                modifier = Modifier
                    .background(
                        when (post.type) {
                            "RESCUE_UPDATE" -> Color(0xFFC0EDCD)
                            "SOS" -> Color(0xFFFFDAD6)
                            else -> Color(0xFFF6F3F2)
                        }
                    )
                    .border(2.dp, Color.Black)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    post.type.replace("_", " "),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = when (post.type) {
                        "SOS" -> Color(0xFF93000A)
                        else -> Color(0xFF1C1B1B)
                    }
                )
            }
        }

        HorizontalDivider(thickness = 3.dp, color = Color.Black)

        // Post Content
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                post.content,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 20.sp
            )
        }

        HorizontalDivider(thickness = 3.dp, color = Color.Black)

        // Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4EFE6))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButton(
                    icon = if (post.isLikedByMe) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    text = "${post.likes}\nPAWS",
                    onClick = onLikeClick,
                    iconTint = if (post.isLikedByMe) Color(0xFFBA1A1A) else Color.Black
                )
                ActionButton(
                    icon = Icons.Default.ChatBubbleOutline,
                    text = "${post.comments}\nNOTES",
                    onClick = onCommentClick
                )
                ActionButton(
                    icon = Icons.Default.BookmarkBorder,
                    text = "SAVE",
                    onClick = {}
                )
            }
            ActionButton(
                icon = Icons.Default.Share,
                text = "",
                onClick = {}
            )
        }
    }
}

// ===================== DESIGN COMPONENTS (kept from original) =====================

@Composable
private fun CommunityTopHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFFCF9F8))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFE24E))
                        .border(2.dp, Color.Black)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("BEZUBAAN", fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(modifier = Modifier.background(Color(0xFFFFE24E)).border(1.dp, Color.Black).padding(horizontal = 4.dp, vertical = 2.dp)) {
                            Text("HELPING HANDS", fontSize = 8.sp, fontWeight = FontWeight.Black, color = Color(0xFF211B00))
                        }
                    }
                    Text("COMMUNITY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFBA1A1A))
                        .border(2.dp, Color.Black)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text("SOS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        }
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
    }
}

@Composable
private fun ImpactTicker() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFFFE24E), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("THIS WEEK", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            TickerItem(Color(0xFF002210), "RESCUES")
            TickerDot()
            TickerItem(Color(0xFFFF5733), "ADOPTIONS")
            TickerDot()
            TickerItem(Color(0xFF0F3822), "FOSTERS")
        }
    }
}

@Composable
private fun TickerItem(dotColor: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).background(dotColor, CircleShape))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
    }
}

@Composable
private fun TickerDot() {
    Text(" • ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B), modifier = Modifier.padding(horizontal = 8.dp))
}

@Composable
private fun MobilizationHeroBar(onPostStoryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(2.dp))
            .background(Color(0xFFF4EFE6), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(10.dp).background(Color(0xFF10B981), CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("BEZUBAAN SQUAD", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFF414942))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Active Rescuers", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
                }
            }
        }
        Button(
            onClick = onPostStoryClick,
            shape = RoundedCornerShape(2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
            modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp)).height(36.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("POST STORY", fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun SegmentSelectionPills() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PillButton("ALL STORIES", Icons.Default.Public, true)
        PillButton("BEFORE & AFTER", Icons.Default.Compare, false)
        PillButton("RECOVERY DIARY", Icons.AutoMirrored.Filled.MenuBook, false)
        PillButton("URGENT S.O.S", Icons.Default.Warning, false, isRed = true)
    }
}

@Composable
private fun PillButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean, isRed: Boolean = false) {
    val bgColor = if (isSelected) Color(0xFF0F3822) else if (isRed) Color(0xFFFFDAD6) else Color(0xFFFBF8F1)
    val contentColor = if (isSelected) Color.White else if (isRed) Color(0xFF93000A) else Color(0xFF1C1B1B)

    Row(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(2.dp))
            .background(bgColor, RoundedCornerShape(2.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, color = contentColor)
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    iconTint: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .border(2.dp, Color.Black)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = iconTint)
        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(text, fontSize = 10.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, lineHeight = 12.sp)
        }
    }
}

@Composable
private fun BottomCallToAction(onShareLogClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(Color(0xFF1C1B1B), RoundedCornerShape(2.dp))
            .border(3.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Pets, contentDescription = null, tint = Color(0xFFFFE24E), modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "THEY HAVE NO VOICE. YOU ARE THEIR ECHO.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Did you help, feed, or rescue a stray animal today? Post your field report to mobilize help and inspire the community.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFC1C8C0),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onShareLogClick,
            modifier = Modifier.fillMaxWidth().height(48.dp).border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE24E), contentColor = Color(0xFF121212)),
            shape = RoundedCornerShape(2.dp)
        ) {
            Icon(Icons.Default.AddBox, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("SHARE A RESCUE LOG", fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
    }
}

// ===================== HELPERS =====================

private fun formatTimestamp(raw: String): String {
    if (raw.isBlank()) return "Just now"
    return try {
        // Parse ISO 8601 and show relative time
        val instant = java.time.Instant.parse(raw)
        val now = java.time.Instant.now()
        val duration = java.time.Duration.between(instant, now)
        when {
            duration.toMinutes() < 1 -> "Just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()}m ago"
            duration.toHours() < 24 -> "${duration.toHours()}h ago"
            duration.toDays() < 7 -> "${duration.toDays()}d ago"
            else -> raw.take(10)
        }
    } catch (e: Exception) {
        "Recently"
    }
}
