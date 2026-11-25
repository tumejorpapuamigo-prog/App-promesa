package com.promesa.app

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(64, 64, 64, 64)
        }

        val texto = TextView(this).apply {
            text = "Para que Promesa funcione, activa su servicio en Accesibilidad."
            textSize = 18f
            gravity = Gravity.CENTER
        }

        val boton = Button(this).apply {
            text = "Abrir Accesibilidad"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        layout.addView(texto)
        layout.addView(boton)
        setContentView(layout)
    }
}
