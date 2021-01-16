package com.example.android.syncgatewaysampleapp.universities

import com.couchbase.lite.*
import com.couchbase.lite.Function
import com.example.android.syncgatewaysampleapp.BasePresenter
import com.example.android.syncgatewaysampleapp.profile.UserProfileContract
import com.example.android.syncgatewaysampleapp.util.DatabaseManager
import java.util.HashMap

class UniversitiesPresenter(private var view: UniversitiesContract.View)
    : BasePresenter, UniversitiesContract.Presenter {

    override fun start() {
        return
    }

    override fun fetchUniversities(name: String) {
        fetchUniversities(name, null)
    }

    override fun fetchUniversities(name: String, country: String?) {
        val database = DatabaseManager.getUniversityDatabase()

        var whereQueryExpression = Function.lower(Expression.property("name")).like(Expression.string("%" + name.toLowerCase() + "%"))

        if (country != null && !country.isEmpty()) {
            var countryQueryExpression = Function.lower(Expression.property("country")).like(Expression.string("%" + country.toLowerCase() + "%"))

            whereQueryExpression = whereQueryExpression.and(countryQueryExpression)
        }

        val query: Query = QueryBuilder.select(SelectResult.all())
                                       .from(DataSource.database(database))
                                       .where(whereQueryExpression)

        var rows: ResultSet? = null

        try {
            rows = query.execute()
        } catch (ex: CouchbaseLiteException) {
            ex.printStackTrace()
            return
        }

        val data: MutableList<Map<String, Any>> = ArrayList()

        var row: Result?

        while ((rows.next().also { row = it }) != null)
        {
            // tag::university[]
            /**
             * instance of UniversityRecord via HashMap
             */
            val properties: MutableMap<String, Any> = HashMap()
            properties["name"] = row?.getDictionary("universities")!!.getString("name")
            properties["country"] = row?.getDictionary("universities")!!.getString("country")
            properties["web_pages"] = row?.getDictionary("universities")!!.getArray("web_pages")
            // end::university[]
            data.add(properties)
        }

        view.showUniversities(data)
    }




}