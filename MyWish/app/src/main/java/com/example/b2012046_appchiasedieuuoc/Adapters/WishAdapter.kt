package com.akuro.mywish.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.b2012046_appchiasedieuuoc.Models.Wish
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.ItemWishBinding


class WishAdapter(
    private val context: Context,
    private val wishList: List<Wish>,
    private val appSharedPreferences: AppSharedPreferences,
    //truyen interface tu ben ngoai vao
    private val iClickItemWish: IClickItemWish,

    //chỉ định đổi số kiểu
) : RecyclerView.Adapter <WishAdapter.WishViewHolder>(){

    class WishViewHolder(val binding: ItemWishBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        return WishViewHolder(ItemWishBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return wishList.size
    }
    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        //lay ra doi tuong wish o vi tri position va thiet lap len giao dien
        val wish: Wish = wishList[position]
        holder.binding.tvName.text = wish.name
        holder.binding.tvContent.text = wish.content
        Glide.with(context).load(wish.owner.avatar)
            .error(R.drawable.img_avt_def)
            .into(holder.binding.imgAvatar)

        if(appSharedPreferences.getIdUser("idUser").toString() == wish.owner._id){
            holder.binding.imgUpdate.visibility= View.VISIBLE
            holder.binding.imgDelete.visibility = View.VISIBLE
        }

        holder.binding.imgDelete.setOnClickListener {
            iClickItemWish.onClickRemove(wish._id)
        }

        holder.binding.imgUpdate.setOnClickListener {
            iClickItemWish.onClickUpdate(wish._id, wish.name,wish.content)
        }
    }


    // dinh nghia 1 interface chua cac ham tkhi thuc hien click tung item
    interface IClickItemWish {
        fun onClickUpdate(idWish: String, fullName:String, content:String)

        fun onClickRemove(idWish: String)
    }
}

