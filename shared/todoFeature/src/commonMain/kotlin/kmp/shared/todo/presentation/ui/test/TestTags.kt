package kmp.shared.todo.presentation.ui.test

object TestTags {

    object TaskListScreen {
        object Loader : TestTag("loader")
        object List : TestTag("task_list")
        object NoTasks : TestTag("no_tasks")
        object Task : TestTag("task")
    }

    object TaskDetailScreen {
        object Loader : TestTag("loader")
        object CompleteButton : TestTag("complete_button")
        object InCompleteButton : TestTag("incomplete_button")
        object TaskDetail : TestTag("task_detail")
    }
}
