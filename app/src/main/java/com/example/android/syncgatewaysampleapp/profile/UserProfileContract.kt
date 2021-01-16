package com.example.android.syncgatewaysampleapp.profile

import java.util.HashMap

interface UserProfileContract {

    interface View {
        fun showProfile(profile: Map<String, Any>)
    }

    interface Presenter {
        fun fetchProfile()
        fun saveProfile(profile: HashMap<String, Any>)
    }

}