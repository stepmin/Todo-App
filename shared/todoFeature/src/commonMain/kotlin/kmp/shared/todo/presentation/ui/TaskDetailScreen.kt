package kmp.shared.todo.presentation.ui

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.presentation.ui.components.MultilineHintTextField
import kmp.shared.todo.presentation.ui.test.TestTags
import kmp.shared.todo.presentation.ui.test.testTag
import kmp.shared.todo.presentation.vm.TaskDetailState

@Composable
fun TaskDetailScreen(
    state: TaskDetailState,
    modifier: Modifier = Modifier,
    markTask: (Task) -> Unit,
    onNoteChange: (String) -> Unit,
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
//                Spacer(modifier = Modifier.weight(1f)) // Pushes the middle item to the center

                state.task?.title?.let {
                    MultilineHintTextField(
                        value = state.task.title,
                        onValueChanged = {
                            onNoteChange(it)
                        },
                        hintText = "Enter your text here...",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes the middle item to the center


                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        onClick = {
                            state.task?.id?.let {
                                markTask(state.task)
                            }
                        },
                        modifier = Modifier.weight(1f).height(120.dp).padding(start = 8.dp)
                            .testTag(if (state.task?.completed == true) TestTags.TaskDetailScreen.InCompleteButton else TestTags.TaskDetailScreen.CompleteButton),
                    ) {
                        Text(if (state.task?.completed == true) "Mark as Incomplete" else "Mark as Completed")
                    }
                }
            }
        }
    }
}