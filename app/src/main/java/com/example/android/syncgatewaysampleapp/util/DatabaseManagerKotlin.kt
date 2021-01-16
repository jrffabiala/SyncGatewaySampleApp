package com.example.android.syncgatewaysampleapp.util

import android.content.Context
import android.util.Log
import com.couchbase.lite.*

/**
 * Singleton class
 */

object DatabaseManagerKotlin {

    private lateinit var userProfileDatabase: Database

    val userProfileDbName = "user_profile"

    private var listenerToken: ListenerToken? = null
    var currentUser: String? = null


    init {
        println("DatabaseManager invoked.")
    }

    fun initializeCouchbaseLite(context: Context) {

        CouchbaseLite.init(context)

        println("Database initialized.")
    }

    //
    fun openOrCreateDatabase(context: Context, username: String) {
        var config: DatabaseConfiguration = DatabaseConfiguration()

        config.directory = String.format("%s/%s", context.filesDir, username)

        currentUser = username

        try {

            userProfileDatabase = Database(userProfileDbName, config)
            registerForDatabaseChanges();
        } catch (e : CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    fun closeDatabase() {
        try {

        } catch (ex : CouchbaseLiteException) {
            ex.printStackTrace()
        }
    }

    private fun registerForDatabaseChanges() {
        listenerToken = userProfileDatabase.addChangeListener(DatabaseChangeListener { change ->
            if (change != null) {
                for (docId in change.documentIDs) {
                    val doc: Document = userProfileDatabase.getDocument(docId)
                    if (doc != null) {
                        Log.i("DatabaseChangeEvent", "Document was added/updated")
                    } else {
                        Log.i("DatabaseChangeEvent", "Document was deleted")
                    }
                }
            }
        })
    }


}