package com.example.chefapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.chefapp.databinding.ActivityMainBinding
import com.example.chefapp.fragments.*

class MainActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMainBinding

    // Toggle para el icono hamburguesa del Drawer
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "🍳 ChefApp"

        // Configurar Navigation Drawer
        configurarDrawer()

        // Cargar LeftFragment fijo
        cargarFragmentoIzquierdo()

        // Cargar HomeFragment por defecto en el panel derecho
        cargarFragmentoDerecho(PerfilFragment(), "perfil")    }

    // Configura el DrawerLayout con el toggle
    private fun configurarDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Eventos del NavigationView
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            manejarItemDrawer(menuItem)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Carga LeftFragment en el panel izquierdo fijo
    private fun cargarFragmentoIzquierdo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLeft, LeftFragment())
            .commit()
    }

    // Reemplaza el fragmento derecho
    fun cargarFragmentoDerecho(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameRight, fragment, tag)
            .commit()

        // Actualizar item activo en LeftFragment
        val leftFrag = supportFragmentManager.findFragmentById(R.id.frameLeft) as? LeftFragment
        leftFrag?.setItemActivo(tag)
    }

    // Maneja seleccion de items del Navigation Drawer
    private fun manejarItemDrawer(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_perfil   -> cargarFragmentoDerecho(PerfilFragment(), "perfil")
            R.id.nav_fotos    -> cargarFragmentoDerecho(FotosFragment(), "fotos")
            R.id.nav_video    -> cargarFragmentoDerecho(VideoFragment(), "video")
            R.id.nav_web      -> cargarFragmentoDerecho(WebFragment(), "web")
            R.id.nav_botones  -> cargarFragmentoDerecho(BotonesFragment(), "botones")
            R.id.nav_cerrar   -> cerrarSesion()
        }
    }

    // Cierra sesion — vuelve al Splash
    private fun cerrarSesion() {
        finish()
    }

    // Manejar boton Atras — cierra Drawer si esta abierto
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}