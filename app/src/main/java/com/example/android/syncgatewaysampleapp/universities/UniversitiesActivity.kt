package com.example.android.syncgatewaysampleapp.universities

import android.content.Intent
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.syncgatewaysampleapp.BaseActivity
import com.example.android.syncgatewaysampleapp.R
import com.example.android.syncgatewaysampleapp.util.UniversitiesAdapter

class UniversitiesActivity: BaseActivity(), UniversitiesContract.View {
    private lateinit var presenter: UniversitiesContract.Presenter

    var recyclerView: RecyclerView? = null
    var svNameSearch: SearchView? = null
    var svCountrySearch: SearchView? = null

    override val layoutId: Int
        get() = R.layout.activity_universities

    override fun onCreateBaseActivity() {

        svNameSearch = findViewById(R.id.svName)
        svCountrySearch = findViewById(R.id.svCountry)

        recyclerView = findViewById(R.id.universityList)
        recyclerView?.setHasFixedSize(true)

        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView?.addItemDecoration(dividerItemDecoration)
        recyclerView?.layoutManager = layoutManager

        val universityAdapter: UniversitiesAdapter = UniversitiesAdapter(ArrayList<Map<String, Any>>())
        recyclerView?.adapter = universityAdapter

        presenter = UniversitiesPresenter(this)
    }

    fun onSearchTapped(view: View) {

        if (svNameSearch?.query?.length!! > 0) {
            if (svCountrySearch?.query?.length!! > 0) {
                presenter.fetchUniversities(svNameSearch?.query.toString(), svCountrySearch?.query.toString())
            } else {
                presenter.fetchUniversities(svNameSearch?.query.toString())
            }
        }
    }

    override fun showUniversities(universities: List<Map<String, Any>>) {
        val adapter = UniversitiesAdapter(universities)
        adapter.setOnItemClickListener(object : UniversitiesAdapter.OnItemListener {
            override fun onClick(view: View?, position: Int) {
                val selectedUniversity = universities[position]["name"].toString()
                val returnIntent = Intent()
                returnIntent.putExtra("result", selectedUniversity)
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        })
        recyclerView?.adapter = adapter
        recyclerView?.invalidate()
    }

}