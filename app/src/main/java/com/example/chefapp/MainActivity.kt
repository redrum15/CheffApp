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

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "🍳 ChefApp"

        configurarDrawer()
        cargarFragmentoIzquierdo()
        cargarFragmentoDerecho(PerfilFragment(), "perfil")    }

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

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            manejarItemDrawer(menuItem)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun cargarFragmentoIzquierdo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLeft, LeftFragment())
            .commit()
    }

    fun cargarFragmentoDerecho(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameRight, fragment, tag)
            .commit()

        val leftFrag = supportFragmentManager.findFragmentById(R.id.frameLeft) as? LeftFragment
        leftFrag?.setItemActivo(tag)
    }

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

    private fun cerrarSesion() {
        finish()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}