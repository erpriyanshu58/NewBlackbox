package top.niunaijun.blackboxa.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.niunaijun.blackbox.BlackBoxCore
import top.niunaijun.blackboxa.R


class ShortcutActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pkg = intent.getStringExtra("pkg")
        val userID = intent.getIntExtra("userId",0)

        lifecycleScope.launch(Dispatchers.IO) {
            if (pkg.isNullOrBlank()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ShortcutActivity, R.string.start_fail, Toast.LENGTH_SHORT).show()
                    finish()
                }
                return@launch
            }
            val result = BlackBoxCore.get().launchApk(pkg, userID)
            withContext(Dispatchers.Main) {
                if (!result) {
                    Toast.makeText(this@ShortcutActivity, R.string.start_fail, Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}