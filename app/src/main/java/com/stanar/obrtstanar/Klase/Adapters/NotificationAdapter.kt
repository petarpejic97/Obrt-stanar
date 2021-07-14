package com.stanar.obrtstanar.Klase.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stanar.obrtstanar.Klase.FirebaseClass.Notification
import com.stanar.obrtstanar.R
import kotlinx.android.synthetic.main.notification_item.view.*

class NotificationAdapter(notifications : MutableList<Notification>) : RecyclerView.Adapter<NotificationViewHolder>() {
    var notifications : MutableList<Notification> = mutableListOf()

    init {
        this.notifications = notifications
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent,false)
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification : Notification = notifications[position]
        holder.bind(notification)
    }
}
class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvTitle : TextView
    var tvNotificationText : TextView
    var tvDate : TextView
    init {
        tvTitle = itemView.findViewById(R.id.tvTitle)
        tvNotificationText = itemView.findViewById(R.id.tvText)
        tvDate = itemView.findViewById(R.id.tvDate)
    }
    fun bind(notification : Notification){
        itemView.tvTitle.text = notification.title
        itemView.tvDate.text = notification.date
        itemView.tvText.text = notification.notificationText
    }

}