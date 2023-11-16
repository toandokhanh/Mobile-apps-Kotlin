package com.example.mymap

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.Models.UserMap

private const val TAG = "MapsAdapter"
class MapsAdapter(
    val context: Context,
    val userMaps: List<UserMap>,
    val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.MyViewHolder>()
    {
        interface OnClickListener {
            fun onItemClick(position: Int)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.row_place, parent, false)
            return MyViewHolder(view)
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val userMap = userMaps[position]

            //Toast.makeText(context, userMaps.size.toString(), Toast.LENGTH_SHORT).show()
            val tvTitle = holder.itemView.findViewById<TextView>(R.id.tv_place)
            tvTitle.text = userMap.title

            holder.itemView.setOnClickListener {
                Log.i(TAG, "Click on position $position")
                onClickListener.onItemClick(position)
            }
        }

        override fun getItemCount(): Int = userMaps.size





    }
