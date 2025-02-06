package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.presentation.ui.test.TestTags
import kmp.shared.samplecomposenavigation.presentation.ui.test.testTag
import kmp.shared.samplecomposenavigation.presentation.vm.TodoListState

@Composable
fun TodoListScreen(
    state: TodoListState,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state.loading,
        label = "AnimatedLoading",
    ) { loading ->
        if (loading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                // Task Count
                Text(
                    text = "${state.tasks?.size ?: 0} Tasks",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                if (state.tasks?.isNotEmpty() == true) {
                    // Task List
                    TaskList(
                        modifier = Modifier.testTag(TestTags.TodoListScreen.TodoList),
                        tasks = state.tasks,
                        onTaskChecked = { /* Handle check */ },
                    )
                } else {
                    Text(
                        text = "No tasks",
                        modifier = Modifier.testTag(TestTags.TodoListScreen.NoTasks),
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskChecked: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(tasks.size) { it: Int ->
            val task = tasks[it]
            TaskItem(
                task = task,
                onCheckedChange = { onTaskChecked(task.id) },
            )
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag(TestTags.TodoListScreen.Task),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = { onCheckedChange(it) },
        )
        Text(
            text = task.title,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            fontSize = 16.sp,
        )
    }
}