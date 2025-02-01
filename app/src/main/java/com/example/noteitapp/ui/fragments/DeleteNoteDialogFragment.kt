package com.example.noteitapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.noteitapp.R
import com.example.noteitapp.data.Note

class DeleteNoteDialogFragment : DialogFragment() {

    private var listener: DeleteNoteListener? = null

    // Interface to communicate with the parent Activity/Fragment
    interface DeleteNoteListener {
        fun onDeleteConfirmed(note: Note)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for the dialog
        return inflater.inflate(R.layout.fragment_delete_note_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the note from arguments
        val note = arguments?.getParcelable<Note>("note")

        // Set up buttons
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dismiss() // Close the dialog
        }

        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            // Notify the listener that the user confirmed the deletion
            listener?.onDeleteConfirmed(note!!)
            dismiss() // Close the dialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the parent Activity/Fragment implements the DeleteNoteListener interface
        if (context is DeleteNoteListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement DeleteNoteListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance(note: Note): DeleteNoteDialogFragment {
            val fragment = DeleteNoteDialogFragment()
            val args = Bundle().apply {
                putParcelable("note", note)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
