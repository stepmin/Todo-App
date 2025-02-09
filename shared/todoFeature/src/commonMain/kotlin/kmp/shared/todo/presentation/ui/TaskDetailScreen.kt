package kmp.shared.todo.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp.shared.todo.presentation.ui.test.TestTags
import kmp.shared.todo.presentation.ui.test.testTag
import kmp.shared.todo.presentation.vm.TaskDetailState
import kotlinx.coroutines.NonCancellable.isCompleted

@Composable
fun TaskDetailScreen(
    state: TaskDetailState,
    modifier: Modifier = Modifier,
    markAsCompleted: (Int) -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state.loading,
        label = "AnimatedLoading",
    ) { loading ->
        if (loading) {
            Box(
                modifier = Modifier
                    .testTag(TestTags.TaskDetailScreen.Loader)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MaterialTheme.colors.background)
                    .testTag(TestTags.TaskDetailScreen.TaskDetail),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                state.taskDetail?.name?.let {
                    Divider()

                    // Description Section
                    Text(
                        text = "User",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = state.taskDetail.name,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes the middle item to the center

                state.taskDetail?.title?.let {
                    TaskTitleItem(taskTitle = state.taskDetail.title, isCompleted = isCompleted)
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes the middle item to the center


                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    /*Button(
                        onClick = {},
                        modifier = Modifier.weight(1f).height(150.dp).padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    ) {
                        Text("Back")
                    }*/
                    Button(
                        onClick = {
                            state.taskDetail?.id?.let {
                                markAsCompleted(it)
                            }
                        },
                        modifier = Modifier.weight(1f).height(120.dp).padding(start = 8.dp),
                    ) {
                        Text(if (state.taskDetail?.completed == true) "Mark as Incomplete" else "Mark as Completed")
                    }
                }
            }
        }
    }
}

@Composable
fun TaskTitleItem(taskTitle: String, isCompleted: Boolean) {
    val transition = rememberInfiniteTransition()
    val animatedColor by transition.animateColor(
        initialValue = if (isCompleted) Color(0xFF4CAF50) else Color(0xFFF44336),
        targetValue = if (isCompleted) Color(0xFF66BB6A) else Color(0xFFE57373),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    Card(
        elevation = 4.dp,
        backgroundColor = animatedColor,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .padding(vertical = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = taskTitle,
                style = MaterialTheme.typography.h4.copy(color = Color.White),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isCompleted) "Completed" else "Incomplete",
                style = MaterialTheme.typography.body1.copy(color = Color.White),
            )
        }
    }
}