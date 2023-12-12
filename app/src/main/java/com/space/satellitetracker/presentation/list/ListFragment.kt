package com.space.satellitetracker.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.satellitetracker.databinding.FragmentListBinding
import com.space.satellitetracker.util.collectLatestLifecycleFlow
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment: Fragment() {

    private lateinit var viewBinding: FragmentListBinding
    private val viewModel: ListViewModel by viewModel()
    private val adapter: SatelliteListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentListBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.satelliteList.adapter = adapter
        viewBinding.satelliteList.layoutManager = LinearLayoutManager(context)
        viewBinding.satelliteList.setHasFixedSize(false)

        viewBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewBinding.searchBar.clearFocus()
                p0?.let { viewModel.onSearchTextChanged(it) }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { viewModel.onSearchTextChanged(it) }
                return false
            }
        })

        collectLatestLifecycleFlow(viewModel.state) {
            if (it.isLoading) {    //loading
                viewBinding.loadingIndicator.visibility = View.VISIBLE
            } else if (it.error != null) {  //error
                viewBinding.loadingIndicator.visibility = View.GONE
                adapter.listDiffer.submitList(emptyList())
                viewBinding.listError.apply {
                    text = it.error
                    visibility = View.VISIBLE
                }
            } else {    //success
                viewBinding.loadingIndicator.visibility = View.GONE
                adapter.listDiffer.submitList(it.satellites)
            }
        }
    }
}