package com.example.android.syncgatewaysampleapp.universities

interface UniversitiesContract {

    interface View {
        fun showUniversities(universities: List<Map<String, Any>>)
    }

    interface Presenter {
        fun fetchUniversities(name: String, country: String?)
        fun fetchUniversities(name: String)
    }
}