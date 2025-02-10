package kmp.shared.todo.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kmp.shared.base.MR
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.presentation.ui.test.TestTags
import kmp.shared.todo.presentation.ui.test.testTag
import kmp.shared.todo.presentation.vm.TaskDetailState
import dev.icerock.moko.resources.compose.stringResource

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
                    .testTag(TestTags.TaskDetailScreen.TaskDetail),
            ) {
                state.task?.title?.let {
                    TextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(16.dp),
                        value = state.task.title,
                        onValueChange = {
                            onNoteChange(it)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    )
                }

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
                        modifier = Modifier.weight(1f)
                            .height(120.dp)
                            .testTag(if (state.task?.completed == true) TestTags.TaskDetailScreen.InCompleteButton else TestTags.TaskDetailScreen.CompleteButton),
                    ) {

                        val incomplete = stringResource(MR.strings.detail_screen_mark_button_incomplete)
                        val complete = stringResource(MR.strings.detail_screen_mark_button_complete)

                        Text(if (state.task?.completed == true) incomplete else complete)
                    }
                }
            }
        }
    }
}