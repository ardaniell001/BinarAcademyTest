package com.binar.ardanil.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.binar.ardanil.R
import com.binar.ardanil.configuration.Constant
import com.binar.ardanil.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_profile.*

class DetailProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.extras?.get(Constant.IntentData.USER) as User

        this.text_name.text = user.first_name+" "+user.last_name
        this.text_email.text = user.email
        Glide.with(this)
            .load(user.avatar)
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .into(this.imageViewDetailUser)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}