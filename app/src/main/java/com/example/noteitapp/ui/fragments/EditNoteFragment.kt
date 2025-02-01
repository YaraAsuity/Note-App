package com.example.noteitapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.noteitapp.R
import com.example.noteitapp.data.Note
import com.example.noteitapp.ui.viewmodels.NoteViewModel

class EditNoteFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_note, container, false)

        // Initialize ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Bind views
        editTitle = view.findViewById(R.id.editTitle)
        editContent = view.findViewById(R.id.editContent)

        // Set up Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack() // Go back to the previous screen
        }

        // Handle save icon click
        val btnSave = view.findViewById<ImageButton>(R.id.btnSave)
        btnSave.setOnClickListener {
            val updatedTitle = editTitle.text.toString()
            val updatedContent = editContent.text.toString()
            if (updatedTitle.isNotEmpty() && updatedContent.isNotEmpty()) {
                val note = arguments?.getParcelable<Note>("note")
                val updatedNote = note?.copy(title = updatedTitle, content = updatedContent)
                if (updatedNote != null) {
                    noteViewModel.update(updatedNote)
                }
                parentFragmentManager.popBackStack() // Go back to the previous screen
            }
        }

        // Get the note from arguments and populate the fields
        val note = arguments?.getParcelable<Note>("note")
        note?.let {
            editTitle.setText(it.title)
            editContent.setText(it.content)
        }

        return view
    }
}