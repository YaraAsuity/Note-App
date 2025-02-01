package com.example.noteitapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.noteitapp.R
import com.example.noteitapp.data.Note

class NoteAdapter(
    private var notes: List<Note>,
    private val onDeleteClickListener: (Note) -> Unit, // Delete click listener
    private val onNoteClickListener: (Note) -> Unit // Note click listener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.subtitleTextView.text = note.subtitle

        // Apply the background color to the note item
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, note.color))

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(note)
        }

        // Handle note item click
        holder.itemView.setOnClickListener {
            onNoteClickListener(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }
}