package com.example.labone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter: RecyclerView.Adapter<PhonesAdapter.PhoneViewHolder>() {

    class PhoneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.phone_list_item_title)
        val price: TextView = itemView.findViewById(R.id.phone_list_item_price_value)
        val date: TextView = itemView.findViewById(R.id.phone_list_item_date_value)
        val score: TextView = itemView.findViewById(R.id.phone_list_item_score_value)
    }

    private var phoneList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(data: ArrayList<PhoneModel>) {
        phoneList.clear()
        phoneList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.phone_list_item, parent, false)
        return PhoneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.title.text = phoneList[position].name
        holder.price.text = phoneList[position].price
        holder.date.text = phoneList[position].date
        holder.score.text = phoneList[position].score
    }

    override fun getItemCount(): Int {
        return phoneList.count()
    }
}