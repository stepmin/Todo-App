package kmp.shared.samplecomposenavigation.presentation.ui.test

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

open class TestTag(val tag: String)

@Stable
fun Modifier.testTag(tag: TestTag) = testTag(tag.tag)
