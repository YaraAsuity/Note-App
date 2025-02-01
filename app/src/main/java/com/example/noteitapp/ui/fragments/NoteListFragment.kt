package com.example.noteitapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteitapp.R
import com.example.noteitapp.databinding.FragmentNoteListBinding
import com.example.noteitapp.ui.adapters.NoteAdapter
import com.example.noteitapp.ui.viewmodels.NoteViewModel

class NoteListFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val adapter = NoteAdapter(
            emptyList(),
            onDeleteClickListener = { note ->
                // Handle delete button click
                val dialog = DeleteNoteDialogFragment.newInstance(note)
                dialog.show(parentFragmentManager, "DeleteNoteDialogFragment")
            },
            onNoteClickListener = { note ->
                // Handle note item click
                val action = NoteListFragmentDirections.actionNoteListFragmentToEditNoteFragment(note)
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let { adapter.setNotes(it) }
        }
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_searchFragment)
        }
        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}