package kmp.shared.todo.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("konsist.every internal or public compose function has a modifier")
@Composable
actual fun PlatformSpecificCheckboxView(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChanged,
        )
    }
}
