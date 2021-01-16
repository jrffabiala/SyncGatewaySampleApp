package com.example.android.syncgatewaysampleapp.profile

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.syncgatewaysampleapp.BaseActivity
import com.example.android.syncgatewaysampleapp.R
import com.example.android.syncgatewaysampleapp.login.MainActivity
import com.example.android.syncgatewaysampleapp.universities.UniversitiesActivity
import com.example.android.syncgatewaysampleapp.util.DatabaseManager

class UserProfileActivity : BaseActivity(), UserProfileContract.View {

    private lateinit var presenter: UserProfileContract.Presenter

    var etName: EditText? = null
    var etEmail: EditText? = null
    var tvUniversity: TextView? = null

    private val PICK_UNIVERSITY = 2

    override val layoutId: Int
        get() = R.layout.activity_user_profile

    override fun onCreateBaseActivity() {

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        tvUniversity = findViewById(R.id.tvSelectUniversity)

        presenter = UserProfilePresenter(this)

        runOnUiThread(Runnable(presenter::fetchProfile))
    }

    override fun showProfile(profile: Map<String, Any>) {
        etName?.setText(profile["name"] as String?)
        etEmail?.setText(profile["email"] as String?)

        val university = profile["university"] as String?

        if (university != null && !university.isEmpty()) {
                tvUniversity?.text = university
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        tvUniversity?.text = data?.getStringExtra("result")
    }

    fun onSaveTapped(view: View) {

        val profile = HashMap<String, Any>()

        profile["name"] = etName!!.text.toString()
        profile["email"] = etEmail!!.text.toString()
        profile["university"] = tvUniversity!!.text.toString()

        presenter.saveProfile(profile)

        Toast.makeText(this, "Update Profile Success.", Toast.LENGTH_SHORT).show()
    }

    fun onLogoutTapped(view: View) {

        DatabaseManager.stopAllReplicationForCurrentUser()
        DatabaseManager.getSharedInstance().closePrebuiltDatabase()
        DatabaseManager.getSharedInstance().closeDatabase()

        val intent: Intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun onUniversityTapped(view: View) {
        val intent: Intent = Intent(applicationContext, UniversitiesActivity::class.java)
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, PICK_UNIVERSITY);
    }
}