package com.binar.ardanil.view.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binar.ardanil.R
import com.binar.ardanil.configuration.Constant
import com.binar.ardanil.model.Profile
import com.binar.ardanil.view.profile.EditProfileActivity
import com.binar.ardanil.viewmodel.ProfileViewModel
import com.binar.ardanil.viewmodelfactory.ProfileVMF
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.Serializable

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var profile: Profile? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = ProfileVMF(requireContext())
        profileViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            profile = it
            this.text_name.text = it.name
            this.text_email.text = it.email
            this.text_phone.text = it.phone
            Glide.with(this)
                .load(Uri.parse(it.avatar))
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_person_24)
                .into(this.imageViewDetailUser)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.buttonEditProfile.setOnClickListener {
            startActivityForResult(
                Intent(activity, EditProfileActivity::class.java)
                    .putExtra(Constant.IntentData.PROFILE, profile as Serializable),
                1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            val extras = data?.getSerializableExtra("newdata") as Profile
            profileViewModel.saveUserProfile(extras)
        }
    }
}