import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskDetailScreen(
    state: TaskDetailState,
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
                    text = "Detail",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}