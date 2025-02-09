package kmp.shared.todo.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.presentation.ui.test.TestTags
import kmp.shared.todo.presentation.ui.test.testTag
import kmp.shared.todo.presentation.vm.TaskListState

@Composable
fun TaskListScreen(
    state: TaskListState,
    modifier: Modifier = Modifier,
    onTaskChecked: (Task) -> Unit,
    onRowTapped: (Task) -> Unit,
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
                        modifier = Modifier.testTag(TestTags.TaskListScreen.List),
                        tasks = state.tasks,
                        onTaskChecked = {
                            onTaskChecked(it)
                        },
                        onRowTapped = {
                            onRowTapped(it)
                        },
                    )
                } else {
                    Text(
                        text = "No tasks",
                        modifier = Modifier.testTag(TestTags.TaskListScreen.NoTasks),
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
    onTaskChecked: (Task) -> Unit,
    onRowTapped: (Task) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(tasks.size) { it: Int ->
            val task = tasks[it]
            TaskItem(
                task = task,
                onCheckedChange = { onTaskChecked(it) },
                onRowTapped = { onRowTapped(task) },
            )
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckedChange: (Task) -> Unit,
    onRowTapped: (Task) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                onRowTapped(task)
            }
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag(TestTags.TaskListScreen.Task),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = { onCheckedChange(task.copy(completed = !task.completed)) },
//            onCheckedChange = { onCheckedChange(task) },
        )
        Text(
            text = task.title,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            fontSize = 16.sp,
        )
    }
}