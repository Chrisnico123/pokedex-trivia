package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.ui.MyPokemon.MyPokemonFragment
import com.example.pokedex.ui.favorite.FavoriteFragment
import com.example.pokedex.ui.Pokemon.PokemonFragment
import com.example.pokedex.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding

    val favFragment : Fragment = FavoriteFragment()
    val myPokemonFragment : Fragment = MyPokemonFragment()
    val pokemonFragment : Fragment = PokemonFragment()
    val profileFragment : Fragment = ProfileFragment()

    val fm : FragmentManager = supportFragmentManager
    var active : Fragment = pokemonFragment

    private lateinit var bottomNavigationMenu : BottomNavigationView
    private lateinit var menu : Menu
    private lateinit var menuItem : MenuItem

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser === null ) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonNavigation()
    }

    private fun buttonNavigation() {
        fm.beginTransaction().add(R.id.container , pokemonFragment).show(pokemonFragment).commit()
        fm.beginTransaction().add(R.id.container , favFragment).hide(favFragment).commit()
        fm.beginTransaction().add(R.id.container , myPokemonFragment).hide(myPokemonFragment).commit()
        fm.beginTransaction().add(R.id.container , profileFragment).hide(profileFragment).commit()

        bottomNavigationMenu = binding.navBottom
        menu = bottomNavigationMenu.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_1 -> {
                    callFragment(0 , pokemonFragment)
                }
                R.id.nav_2 -> {
                    callFragment(1 , favFragment)
                }
                R.id.nav_3 -> {
                    callFragment(2 , myPokemonFragment)
                }
                R.id.nav_4 -> {
                    callFragment(3 , profileFragment)
                }
            }
            true
        }
    }

    private fun callFragment(index : Int , fragment: Fragment) {
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        val transaction = fm.beginTransaction()
        transaction.hide(active)
        transaction.show(fragment)
        transaction.commit()

        active = fragment
    }
}