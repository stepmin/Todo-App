package kmp.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kmp.android.shared.style.AppTheme
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.presentation.ui.TaskDetailScreen
import kmp.shared.todo.presentation.ui.TaskListScreen
import kmp.shared.todo.presentation.vm.TaskDetailState
import kmp.shared.todo.presentation.vm.TaskListState

@Preview(showBackground = true)
@Composable
fun TaskListLoaderPreview() = AppTheme {
    val state = TaskListState(
        loading = true,
        tasks = null,
    )

    TaskListScreen(
        state = state,
        onTaskChecked = {},
        onRowTapped = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() = AppTheme {
    val state = TaskListState(
        loading = false,
        tasks = null,
    )

    TaskListScreen(
        state = state,
        onTaskChecked = {},
        onRowTapped = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TaskDetailLoaderPreview() = AppTheme {
    val state = TaskDetailState(
        loading = true,
        taskDetail = null,
        error = null,
    )

    TaskDetailScreen(
        state = state,
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailPreview() = AppTheme {
    val state = TaskDetailState(
        loading = false,
        taskDetail = TaskDetail(
            id = 1,
            title = "Go to the gym",
            completed = true,
            name = "John Doe",
            username = "",
            email = ""
        ),
        error = null,
    )

    TaskDetailScreen(
        state = state,
    ) {

    }
}

