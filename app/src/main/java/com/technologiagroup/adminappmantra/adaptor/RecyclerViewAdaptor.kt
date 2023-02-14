package com.technologiyagroup.matrajayotish.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technologiagroup.adminappmantra.R
import com.technologiyagroup.matrajayotish.model.user.ResponseBody


class RecyclerViewAdaptor(private val mList: List<ResponseBody>): RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_rcycl_endliss, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdaptor.ViewHolder, position: Int) {
        var data = mList.get(position)
        holder.phone.text =data.user.phone
        holder.name.text = data.user.name
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val phone: TextView = itemView.findViewById(R.id.txtPhone)
        val name:TextView = itemView.findViewById(R.id.txtName)
    }


}