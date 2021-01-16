package com.example.android.syncgatewaysampleapp.profile

import com.couchbase.lite.*
import com.example.android.syncgatewaysampleapp.BasePresenter
import com.example.android.syncgatewaysampleapp.util.DatabaseManager

class UserProfilePresenter(private var view: UserProfileContract.View?)
    : BasePresenter, UserProfileContract.Presenter {

    override fun start() {
        return
    }

    override fun fetchProfile() {

        val database: Database = DatabaseManager.getUserProfileDatabase()

        val docId: String = DatabaseManager.getSharedInstance().currentUserDocId

        val query: Query = QueryBuilder
                    .select(SelectResult.all())
                    .from(DataSource.database(database))
                    .where(Meta.id.equalTo(Expression.string(docId)))

        query.addChangeListener(object: QueryChangeListener {
            override fun changed(change: QueryChange) {
                val rows: ResultSet = change.results

                var row: Result? = null

                val profile: MutableMap<String, Any> = HashMap()

                profile["email"] = DatabaseManager.getSharedInstance().currentUser

                while ((rows.next().also { row = it }) != null)
                {
                    val dictionary: Dictionary? = row?.getDictionary("userprofile")

                    if (dictionary != null) {
                        profile["name"] = dictionary.getString("name")
                        profile["university"] = dictionary.getString("university")
                    }
                }

                view?.showProfile(profile)
            }
        })

        try {
            query.execute()
        } catch (ex: CouchbaseLiteException) {
            ex.printStackTrace()
        }
    }

    override fun saveProfile(profile: HashMap<String, Any>) {

        val database: Database = DatabaseManager.getUserProfileDatabase()

        val docId: String = DatabaseManager.getSharedInstance().currentUserDocId

        val mutableDocument: MutableDocument = MutableDocument(docId, profile)

        try {
            database.save(mutableDocument)
        } catch (ex: CouchbaseLiteException) {
            ex.printStackTrace()
        }
    }


}


