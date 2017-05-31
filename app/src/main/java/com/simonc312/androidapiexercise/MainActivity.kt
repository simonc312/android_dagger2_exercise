package com.simonc312.androidapiexercise

import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

import com.simonc312.androidapiexercise.api.models.Guide
import com.squareup.picasso.Picasso

import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        //interfaces
        UpcomingGuidePresenter.View,
        GuideAdapter.ItemActionHandler {

    @Inject
    lateinit var presenter: UpcomingGuidePresenter
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var customTabsBuilder: CustomTabsIntent.Builder

    private var adapter: GuideAdapter? = null
    private var progressBar: ProgressBar? = null
    private var searchView: SearchView? = null

    init {
        MainApplication.getGuideComponent(this).injectsMainActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSearchView()

        this.progressBar = findViewById(R.id.activity_main_progress_bar) as ProgressBar
        this.progressBar!!.visibility = View.VISIBLE
        this.adapter = GuideAdapter(this, picasso)
        this.adapter!!.setItemActionHandler(this)
        val recyclerView = findViewById(R.id.activity_main_recycler_view) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        this.presenter.get()
    }


    override fun onDestroy() {
        super.onDestroy()
        this.presenter.cancel()

        this.adapter?.setItemActionHandler(null)
        this.adapter = null

        this.progressBar?.clearAnimation()
        this.progressBar = null

        tearDownSearchView()
    }

    //region UpcomingGuidePresenter.View
    override fun update(newGuides: List<Guide>) {
        this.progressBar?.visibility = View.GONE
        this.adapter?.update(newGuides)
    }

    override fun showToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
    //endregion

    //region ItemActionHandler
    override fun onClicked(guide: Guide) {
        val guideUri = Uri.parse(guide.getFullGuideUrl())
        this.customTabsBuilder.build().launchUrl(this, guideUri)
    }
    //endregion

    private fun setUpSearchView() {
        this.searchView = findViewById(R.id.activity_main_search_view) as SearchView
        this.searchView!!.setIconifiedByDefault(false)
        this.searchView!!.queryHint = getString(R.string.upcoming_guide_search_hint)
        this.searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this@MainActivity.presenter.get(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        this.searchView!!.setOnCloseListener {
            this@MainActivity.presenter.get(null)
            false
        }
    }

    private fun tearDownSearchView() {
        this.searchView?.setOnQueryTextListener(null)
        this.searchView?.setOnCloseListener(null)
        this.searchView = null
    }
}
