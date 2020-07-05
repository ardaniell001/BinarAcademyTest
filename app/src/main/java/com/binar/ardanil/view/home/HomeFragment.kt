package com.binar.ardanil.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.ardanil.R
import com.binar.ardanil.adapter.UserAdapter
import com.binar.ardanil.configuration.Constant
import com.binar.ardanil.model.User
import com.binar.ardanil.view.profile.DetailProfileActivity
import com.binar.ardanil.viewmodel.HomeViewModel
import com.binar.ardanil.viewmodelfactory.HomeVMF
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable

class HomeFragment : Fragment(), UserAdapter.OnUserClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var userList : ArrayList<User> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = HomeVMF(requireContext())
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get<HomeViewModel>(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rvHome.layoutManager = linearLayoutManager
        rvHome.isNestedScrollingEnabled = true
        val userAdapter = UserAdapter()
        userAdapter.setItem(userList)
        userAdapter.setUserClickListener(this)
        rvHome.adapter = userAdapter

        homeViewModel.isLoading()?.observe(viewLifecycleOwner, Observer {
            it.let {
                this.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        homeViewModel.getFetchUsers()?.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.data?.forEach { user ->
                    user?.let { data ->
                        homeViewModel.saveUser(data)
                    }
                }
            }
        })

        homeViewModel.userList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                userList.clear()
                it.forEach { s ->
                    val userdata = User (
                        s.id,
                        s.email,
                        s.firstName,
                        s.lastName,
                        s.avatar
                    )
                    userList.add(userdata)
                }
                userAdapter.setItem(userList)
                userAdapter.notifyDataSetChanged()
            }else{
                homeViewModel.fetchUsersFromAPI()
            }
        })
    }

    override fun onClick(v: View?, user: User?, position: Int) {
        startActivity(
            Intent(activity, DetailProfileActivity::class.java)
                .putExtra(Constant.IntentData.USER, user as Serializable)
        )
    }
}