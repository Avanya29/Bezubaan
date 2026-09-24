package com.bezubaan.app.feature.community.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bezubaan.app.feature.community.domain.model.Comment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCommentsBottomSheet(
    comments: List<Comment>,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSubmitComment: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var newCommentText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFFFCF9F8),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(6.dp)
                        .background(Color.Black, RoundedCornerShape(3.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("FIELD NOTES", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1C1B1B))
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 3.dp, color = Color.Black)
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxHeight(0.7f)) {
            // Comments List
            Box(modifier = Modifier.weight(1f)) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF0F3822), strokeWidth = 3.dp)
                    }
                } else if (comments.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "NO NOTES YET\nBe the first to leave a field note.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF414942),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(comments, key = { it.id }) { comment ->
                            CommentItem(comment = comment)
                        }
                    }
                }
            }
            
            // Input Bar
            HorizontalDivider(thickness = 3.dp, color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF4EFE6))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCommentText,
                    onValueChange = { newCommentText = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(2.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
                    placeholder = {
                        Text("Write a field note...", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF8C8C8C))
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(2.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(
                    onClick = {
                        if (newCommentText.isNotBlank()) {
                            onSubmitComment(newCommentText)
                            newCommentText = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFFE24E), RoundedCornerShape(2.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(2.dp))
            .background(Color.White, RoundedCornerShape(2.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFE2F3E8), RoundedCornerShape(2.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(2.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                comment.authorName.take(1).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F3822)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(comment.authorName, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF002210))
                Text("Recently", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF414942))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                comment.content,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1B1B),
                lineHeight = 16.sp
            )
        }
    }
}
