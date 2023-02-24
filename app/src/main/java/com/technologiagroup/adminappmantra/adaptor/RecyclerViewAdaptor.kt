package com.technologiyagroup.matrajayotish.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technologiagroup.adminappmantra.R
import com.technologiagroup.adminappmantra.model.user.UserUpdate
import com.technologiagroup.adminappmantra.ui.CreateUser
import com.technologiagroup.adminappmantra.ui.MainActivity
import com.technologiyagroup.matrajayotish.model.user.ResponseBody
import com.technologiyagroup.matrajayotish.model.user.UserResponse


class RecyclerViewAdaptor(private val mList: List<ResponseBody>,private val context: Context): RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>(){
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
        holder.phone.text ="Phone: "+data.user.phone
        holder.name.text ="Name: "+data.user.name

        holder.btnEdit.setOnClickListener {

            val intent = Intent(context,CreateUser::class.java)
            intent.putExtra("userUpdate",UserUpdate(data.user.name,data.user.dateOfBirth,data.user.timeOfBirth,data.user.placeOfBirth,data.user.paymentStatus,data.user.paymentAmt,data.user.paymentDate,data.user.starId,data.user.id!!) as java.io.Serializable)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val phone: TextView = itemView.findViewById(R.id.txtPhone)
        val name:TextView = itemView.findViewById(R.id.txtName)
        val btnEdit:TextView = itemView.findViewById(R.id.btnEdit)
    }


}