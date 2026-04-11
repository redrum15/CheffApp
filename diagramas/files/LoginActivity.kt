package com.example.chefapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chefapp.databinding.ActivityLoginBinding
import com.example.chefapp.models.UsuarioActual

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotones()
    }

    private fun configurarBotones() {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (validarCampos(email, password)) {
                iniciarSesion(email, password)
            }
        }

        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this, "Conectando con Google...", Toast.LENGTH_SHORT).show()
            // TODO: Integrar Google Sign-In SDK
        }

        binding.tvOlvideContrasena.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isEmpty()) {
                binding.tilEmail.error = "Ingresa tu correo primero"
            } else {
                binding.tilEmail.error = null
                Toast.makeText(
                    this,
                    "Se envió un enlace de recuperación a $email",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.tvRegistrarse.setOnClickListener {
            Toast.makeText(this, "Registro próximamente disponible", Toast.LENGTH_SHORT).show()
            // TODO: Navegar a RegisterActivity cuando esté disponible
            // startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validarCampos(email: String, password: String): Boolean {
        var esValido = true

        // Validar email
        if (email.isEmpty()) {
            binding.tilEmail.error = "El correo es obligatorio"
            esValido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Ingresa un correo válido"
            esValido = false
        } else {
            binding.tilEmail.error = null
        }

        // Validar contraseña
        if (password.isEmpty()) {
            binding.tilPassword.error = "La contraseña es obligatoria"
            esValido = false
        } else if (password.length < 4) {
            binding.tilPassword.error = "Mínimo 4 caracteres"
            esValido = false
        } else {
            binding.tilPassword.error = null
        }

        return esValido
    }

    private fun iniciarSesion(email: String, password: String) {
        // Mostrar estado de carga
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = "Verificando..."

        // Simulación de autenticación con el usuario del modelo
        // TODO: Reemplazar con llamada real a una API o base de datos
        val usuarioValido = email == UsuarioActual.usuario.email ||
                email.isNotEmpty() // Permite cualquier email para demo

        if (usuarioValido) {
            val nombre = UsuarioActual.usuario.nombre
            Toast.makeText(this, "¡Bienvenido de vuelta, $nombre! 🍳", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.text = "Entrar a la cocina"
            binding.tilPassword.error = "Correo o contraseña incorrectos"
        }
    }
}
