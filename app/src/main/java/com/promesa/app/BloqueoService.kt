package com.promesa.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class BloqueoService : AccessibilityService() {

    companion object {
        // Apps bloqueadas (de prueba). Cámbialas por las tuyas.
        val bloqueadas = setOf(
            "com.instagram.android",
            "com.zhiliaoapp.musically",
            "com.facebook.katana"
        )
        // Apps a las que el usuario eligió "continuar igualmente" (hasta cierta hora)
        val permitidas = mutableMapOf<String, Long>()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg !in bloqueadas) return

        val hasta = permitidas[pkg] ?: 0L
        if (System.currentTimeMillis() < hasta) return

        val intent = Intent(this, BloqueoActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("pkg", pkg)
        }
        startActivity(intent)
    }

    override fun onInterrupt() {}
}
