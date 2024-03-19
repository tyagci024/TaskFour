package com.example.taskfour.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskfour.databinding.ItemLayoutBinding
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.utilies.downloadFromURL

class Adapter(private var cryptoList: List<CryptoModel>) :
    RecyclerView.Adapter<Adapter.CryptoListViewHolder>() {
    var onItemClickListener: ((CryptoModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoListViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoListViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.bind(crypto)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(crypto)
        }
    }

    override fun getItemCount() = cryptoList.size

    class CryptoListViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(coin: CryptoModel) {
            binding.apply {
                textviewName.text = coin.name
                textViewCurrency.text = coin.currentPrice.toString()
                imageviewCoin.downloadFromURL(coin.image)
            }
        }
    }

    fun updateList(newList: List<CryptoModel>) {
        cryptoList = newList
        notifyDataSetChanged()
    }
}
