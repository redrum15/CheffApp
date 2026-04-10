package com.example.chefapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chefapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_DURATION = 2500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animarProgressBar()
        Handler(Looper.getMainLooper()).postDelayed({
            irAMainActivity()
        }, SPLASH_DURATION)
    }

    private fun animarProgressBar() {
        val animator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100)
        animator.duration = SPLASH_DURATION
        animator.start()
        animator.addUpdateListener { anim ->
            val progreso = anim.animatedValue as Int
            when {
                progreso < 30 -> binding.tvCargando.text = "Preparando ingredientes..."
                progreso < 60 -> binding.tvCargando.text = "Calentando la cocina..."
                progreso < 90 -> binding.tvCargando.text = "Casi listo..."
                else          -> binding.tvCargando.text = "¡Bienvenido, Chef!"
            }
        }
    }

    private fun irAMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}