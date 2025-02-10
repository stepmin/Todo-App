package kmp.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kmp.android.shared.style.AppTheme
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.presentation.ui.TaskDetailScreen
import kmp.shared.todo.presentation.ui.TaskListScreen
import kmp.shared.todo.presentation.ui.components.MultilineHintTextField
import kmp.shared.todo.presentation.vm.TaskDetailState
import kmp.shared.todo.presentation.vm.TaskListState
import androidx.compose.ui.graphics.Color
import kmp.shared.todo.presentation.ui.CenterAlignedTopAppBar
import kmp.shared.todo.presentation.ui.components.TopBar

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
        task = null,
        error = null,
    )

    TaskDetailScreen(
        state = state,
        markTask = {},
        onNoteChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TaskDetailPreview() = AppTheme {
    val state = TaskDetailState(
        loading = false,
        task = Task(
            id = 1,
            userId = 1,
            title = "Go to the gym",
            completed = true,
        ),
        error = null,
    )

    TaskDetailScreen(
        state = state,
        markTask = {},
        onNoteChange = {},
    )
}

@Preview(showBackground = true)
@Composable
fun CustomMultilineHintTextFieldPreview() = AppTheme {
    MultilineHintTextField(
        value = "",
        onValueChanged = {},
        hintText = "Enter your text here...",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
    )
}

@Preview
@Composable
private fun CenterAlignedTopAppBarPreview() {
    CenterAlignedTopAppBar(
        "title",
    ) {

    }

}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        "title",
    ) {

    }

}