package com.example.noteitapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.noteitapp.R
import com.example.noteitapp.data.Note
import com.example.noteitapp.ui.fragments.DeleteNoteDialogFragment
import com.example.noteitapp.ui.viewmodels.NoteViewModel

class MainActivity : AppCompatActivity(), DeleteNoteDialogFragment.DeleteNoteListener {

    private lateinit var navController: NavController
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        setSupportActionBar(findViewById(R.id.toolbar))

        window.decorView.post {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            if (navHostFragment != null) {
                navController = navHostFragment.navController
                setupActionBarWithNavController(navController)
            }
        }
    }

    override fun onDeleteConfirmed(note: Note) {
        // Delete the note using the ViewModel
        noteViewModel.delete(note)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}