package com.example.newsfeedapp.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.newsfeedapp.R
import com.example.newsfeedapp.common.*
import com.example.newsfeedapp.data.model.Article
import com.example.newsfeedapp.ui.NewsViewModel
import com.example.newsfeedapp.ui.adapter.NewsAdapter
import com.example.newsfeedapp.ui.fragment.wish_list.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.Interaction,
    SearchView.OnQueryTextListener {

    private val viewModel: NewsViewModel by viewModels()

    private val newsAdapter by lazy { NewsAdapter(this) }

    private lateinit var responseList: MutableList<Article>

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     Log.e("a",glide.hashCode().toString())

        responseList = mutableListOf()

        setupRecyclerView()
        observeToNewsLiveData()
    }




    private fun observeToNewsLiveData() {
        viewModel.getNews().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Error -> {
                    swipeRefresh.isRefreshing = false
                    ProgressBar.gone()
                    it.msg?.let { msg -> showToast(msg) }
                    viewModel.getHomeNews()
                }
                is Resource.Loading -> ProgressBar.show()
                is Resource.Success -> {
                    if (it.data != null) {
                        ProgressBar.gone()
                        swipeRefresh.isRefreshing = false
                        newsAdapter.differ.submitList(it.data)
                        responseList.addAll(it.data)  // add the call from api to list in memory to search
                    }
                }
            }
        })
    }


    private fun setupRecyclerView() {
        swipeRefresh.apply {
            setOnRefreshListener {
                responseList.clear()
                //observeToNewsLiveData()
                   viewModel.getHomeNews()
            }
        }

        newsRecycler.apply {
            adapter = newsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Article) {
        val action = HomeFragmentDirections.actionNavExploreToDetailsFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryTextChange(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newsAdapter.differ.submitList(searchQuery(newText, responseList))
        return true
    }

}
