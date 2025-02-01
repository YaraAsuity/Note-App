package com.example.noteitapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteitapp.R
import com.example.noteitapp.databinding.FragmentSearchBinding
import com.example.noteitapp.ui.adapters.NoteAdapter
import com.example.noteitapp.ui.viewmodels.NoteViewModel

class SearchFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        adapter = NoteAdapter(
            emptyList(),
            onDeleteClickListener = { note ->
                // Handle delete button click
                val dialog = DeleteNoteDialogFragment.newInstance(note)
                dialog.show(parentFragmentManager, "DeleteNoteDialogFragment")
            },
            onNoteClickListener = { note ->
                // Handle note item click
                val action = SearchFragmentDirections.actionSearchFragmentToEditNoteFragment(note)
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchNotes(it)
                }
                return true
            }
        })
    }

    private fun searchNotes(query: String) {
        noteViewModel.searchNotes("%$query%").observe(viewLifecycleOwner) { notes ->
            notes?.let { adapter.setNotes(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}