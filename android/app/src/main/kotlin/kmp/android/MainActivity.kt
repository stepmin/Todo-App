package kmp.android

import androidx.activity.compose.setContent
import kmp.android.shared.core.system.BaseActivity
import kmp.android.shared.style.AppTheme
import kmp.android.ui.Root

class MainActivity : BaseActivity() {

    override fun onStart() {
        super.onStart()
        setContent {
            AppTheme {
                Root()
            }
        }
    }
}
