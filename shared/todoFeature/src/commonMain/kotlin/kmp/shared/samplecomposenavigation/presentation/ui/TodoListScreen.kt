package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Todoey") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu click */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle add task */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        },
        content = { PaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues)
                    .padding(16.dp)
            ) {
                // Task Count
                Text(
                    text = "3 Tasks",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Task List
                TaskList(
                    tasks = listOf("Buy Bread", "Buy Milk", "Go to the gym"),
                    onTaskChecked = { /* Handle check */ },
                    onTaskDeleted = { /* Handle delete */ }
                )
            }
        }
    )
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<String>,
    onTaskChecked: (Int) -> Unit,
    onTaskDeleted: (Int) -> Unit
) {
    Column {
        tasks.forEachIndexed { index, task ->
            TaskItem(
                task = task,
                onCheckedChange = { onTaskChecked(index) },
                onDelete = { onTaskDeleted(index) }
            )
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: String,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = { onCheckedChange(it) }
        )
        Text(
            text = task,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            fontSize = 16.sp
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Task")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TodoeyAppPreview() {
//    TodoeyApp()
//}
