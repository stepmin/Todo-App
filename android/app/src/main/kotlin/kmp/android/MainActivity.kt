package kmp.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import kmp.android.shared.core.system.BaseActivity
import kmp.android.shared.style.AppTheme
import kmp.android.ui.Root

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            AppTheme {
                Root()
            }
        }
    }
}
