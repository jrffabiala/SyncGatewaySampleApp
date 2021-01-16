package com.example.android.syncgatewaysampleapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.syncgatewaysampleapp.R

class UniversitiesAdapter(private val universities: List<Map<String, Any>>) : RecyclerView.Adapter<UniversitiesAdapter.ViewHolder?>() {
    private var onItemListener: OnItemListener? = null

    interface OnItemListener {
        fun onClick(view: View?, position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.university_list_item, parent, false)
        view.setPadding(20, 20, 20, 20)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = universities[position]["name"].toString()
        holder.subtitle.text = universities[position]["country"].toString()
        holder.itemView.setOnClickListener(View.OnClickListener { view -> onItemListener!!.onClick(view, position) })
    }

    override fun getItemCount(): Int {
        return universities.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemListener) {
        onItemListener = onItemClickListener
    }

}