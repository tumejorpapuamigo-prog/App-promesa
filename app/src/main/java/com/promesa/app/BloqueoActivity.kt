package com.promesa.app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class BloqueoActivity : AppCompatActivity() {

    private var paso = 1
    private lateinit var texto: TextView
    private lateinit var btnCerrar: Button
    private lateinit var btnContinuar: Button
    private var pkg = ""

    // Mensajes fijos por ahora; luego los generará la IA a partir de tu promesa
    private val mensaje1 = "Te prometiste dejar esto. ¿Qué cambió en los últimos minutos? Esa ganas pasa. Tu promesa sigue ahí."
    private val mensaje2 = "Este es el último mensaje. No te voy a detener, porque esta es tu batalla, no la mía. Si continúas, rompes tu promesa. Tú eliges."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pkg = intent.getStringExtra("pkg") ?: ""

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.BLACK)
            setPadding(64, 64, 64, 64)
        }

        texto = TextView(this).apply {
            textSize = 22f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 96)
        }

        btnCerrar = Button(this).apply {
            text = "Cerrar y volver a otro sitio"
            setOnClickListener { irAlInicio() }
        }

        btnContinuar = Button(this).apply {
            text = "Continuar igualmente"
            setOnClickListener { alContinuar() }
        }

        layout.addView(texto)
        layout.addView(btnCerrar)
        layout.addView(btnContinuar)
        setContentView(layout)

        texto.text = mensaje1

        // El botón atrás también te saca de la app bloqueada
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { irAlInicio() }
        })
    }

    private fun alContinuar() {
        if (paso == 1) {
            paso = 2
            texto.text = mensaje2
        } else {
            // Permite la app durante 10 minutos
            BloqueoService.permitidas[pkg] = System.currentTimeMillis() + 10 * 60 * 1000
            finish()
        }
    }

    private fun irAlInicio() {
        val inicio = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(inicio)
        finish()
    }
}
