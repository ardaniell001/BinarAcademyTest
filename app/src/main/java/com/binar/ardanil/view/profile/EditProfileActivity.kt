package com.binar.ardanil.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.binar.ardanil.R
import com.binar.ardanil.configuration.Constant
import com.binar.ardanil.model.Profile
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.Serializable

class EditProfileActivity : AppCompatActivity() {
    private var profileImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val profile = intent.extras?.get(Constant.IntentData.PROFILE) as Profile

        this.editTextName.setText(profile.name)
        this.editTextEmail.setText(profile.email)
        this.editTextPhone.setText(profile.phone)
        Glide.with(this)
            .load(Uri.parse(profile.avatar))
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_person_24)
            .error(R.drawable.ic_baseline_person_24)
            .into(this.imageViewEditUser)

        this.buttonSubmit.setOnClickListener {
            if (validationInput()){
                val mProfile = Profile(
                    editTextName.text.toString(),
                    editTextEmail.text.toString(),
                    editTextPhone.text.toString(),
                    profileImage
                )
                setResult(Activity.RESULT_OK, Intent().putExtra("newdata", mProfile as Serializable))
                finish()
            }
        }

        this.imageViewEditUser.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 99)
        }
    }

    private fun validationInput(): Boolean {
        var valid = true

        if (editTextName.length() <= 0){
            setupError(editTextName)
            valid = false
        }

        if (editTextEmail.length() <= 0){
            setupError(editTextEmail)
            valid = false
        }

        if (editTextPhone.length() <= 0){
            setupError(editTextPhone)
            valid = false
        }

        return valid
    }

    private fun setupError(editText: EditText){
        editText.clearFocus()
        editText.error = "Anda harus mengisi kolom ini"
        editText.requestFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 99 && resultCode === Activity.RESULT_OK) {
            if (data != null) {
                val imageUri = data.data
                profileImage = imageUri.toString()
                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(this.imageViewEditUser)
            }
        }
    }
}