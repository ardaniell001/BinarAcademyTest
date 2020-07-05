package com.binar.ardanil.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.ardanil.configuration.Session
import com.binar.ardanil.model.Profile

class ProfileViewModel(context: Context) : ViewModel() {

    private var session: Session = Session(context)
    private var userProfile : MutableLiveData<Profile> = MutableLiveData()

    init {
        if (!session.isProfileExist()){
            val profileData = Profile(
                "Ardanil Maulana",
                "ardanil.dev@gmail.com",
                "082386730796",
                ""
            )
            session.setUser(profileData)
        }
        userProfile.value = session.getUser()

    }

    fun getUser(): LiveData<Profile>{
        return userProfile
    }

    fun saveUserProfile(profile: Profile){
        session.setUser(profile)
        userProfile.value = profile
    }

}