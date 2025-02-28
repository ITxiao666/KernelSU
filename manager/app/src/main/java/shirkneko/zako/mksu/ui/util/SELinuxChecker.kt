package shirkneko.zako.mksu.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.topjohnwu.superuser.Shell
import shirkneko.zako.mksu.R

@Composable
fun getSELinuxStatus(): String {
    val shell = Shell.Builder.create().build()

    val list = ArrayList<String>()
    val result = shell.newJob().add("getenforce").to(list, list).exec()
    val output = result.out.joinToString("\n").trim()

    return when {
        result.isSuccess -> {
            when (output) {
                "Enforcing" -> stringResource(R.string.selinux_status_enforcing)
                "Permissive" -> stringResource(R.string.selinux_status_permissive)
                "Disabled" -> stringResource(R.string.selinux_status_disabled)
                else -> stringResource(R.string.selinux_status_unknown)
            }
        }
        output.contains("Permission denied") -> stringResource(R.string.selinux_status_enforcing)
        else -> stringResource(R.string.selinux_status_unknown)
    }
}