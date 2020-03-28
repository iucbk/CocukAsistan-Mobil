package com.iucbk.cocuk_asistan.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 28.03.2020 - 17:35          │
//└─────────────────────────────┘

object PermissionUtil {

    private fun hasM() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun askPermissions(
        fragment: Fragment,
        permissions: Array<String>,
        requestCode: Int,
        onPermissionsNotGranted: ((@ParameterName(name = "permissions") Array<String>, requestCode: Int) -> Unit)? = null,
        onAllPermissionsGranted: ((@ParameterName(name = "permissions") Array<String>, requestCode: Int) -> Unit)? = null
    ) {

        val notGrantedPermissions = checkPermissions(
            fragment.requireContext(),
            requestCode,
            permissions,
            onAllPermissionsGranted
        )

        if (!notGrantedPermissions.isNullOrEmpty()) {
            if (onPermissionsNotGranted != null) {
                onPermissionsNotGranted.invoke(notGrantedPermissions, requestCode)
            } else {
                fragment.requestPermissions(permissions, requestCode)
            }
        }
    }

    private fun checkPermissions(
        context: Context,
        requestCode: Int,
        permissions: Array<String>,
        onAllPermissionsGranted: ((@ParameterName(name = "permissions") Array<String>, requestCode: Int) -> Unit)? = null
    ): Array<String>? {
        if (!hasM()) {
            onAllPermissionsGranted?.invoke(permissions, requestCode)
            return null
        }

        val notGrantedPermissions = filterNotGrantedPermissions(permissions, context)
        if (notGrantedPermissions.isEmpty()) {
            onAllPermissionsGranted?.invoke(permissions, requestCode)
            return null
        }
        return notGrantedPermissions
    }

    fun filterNotGrantedPermissions(
        permissions: Array<String>,
        context: Context
    ): Array<String> {
        return if (hasM()) {
            permissions.filter {
                PermissionChecker.checkSelfPermission(
                    context,
                    it
                ) != PermissionChecker.PERMISSION_GRANTED
            }.toTypedArray()
        } else {
            emptyArray()
        }
    }

    fun hasPermissions(context: Context, permList: Array<String>) =
        permList.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
}
