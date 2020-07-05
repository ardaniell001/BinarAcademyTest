package com.binar.ardanil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.ardanil.R
import com.binar.ardanil.model.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object {
        private var userClickListener: OnUserClickListener? = null
    }

    private var users: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position], position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setItem(users: ArrayList<User>) {
        this.users = users
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var user: User? = null
        private var itemPosition = 0

        fun bind(user: User, position: Int) {
            this.user = user
            itemPosition = position

            itemView.textViewName.text = "${user.first_name} ${user.last_name}"
            itemView.textViewEmail.text = user.email

            Glide.with(itemView)
                .load(user.avatar)
                .centerCrop()
                .placeholder(R.drawable.default_image)
                .into(itemView.imageViewUser)
        }

        private val item: User?
            get() = user

        override fun onClick(v: View) {
            if (userClickListener == null) return
            userClickListener?.onClick(v, item, itemPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setUserClickListener(listener: OnUserClickListener?) {
        userClickListener = listener
    }

    interface OnUserClickListener {
        fun onClick(v: View?, user: User?, position: Int)
    }
}