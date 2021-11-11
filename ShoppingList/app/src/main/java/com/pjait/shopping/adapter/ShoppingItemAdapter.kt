package com.pjait.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.pjait.shopping.data.db.entity.ShoppingItem
import com.pjait.shopping.databinding.ShoppingItemBinding
import com.pjait.shopping.ui.shoppingList.ShoppingViewModel

class ShoppingItemAdapter(private val shoppingViewModel: ShoppingViewModel):
    RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    private var items = emptyList<ShoppingItem>()
    class ShoppingViewHolder(val binding: ShoppingItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ShoppingItemBinding.inflate(LayoutInflater.from(parent.context))
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.tvName.text = currentItem.name
        holder.binding.tvAmount.text = currentItem.amount.toString()
        holder.binding.tvPrice.text = currentItem.price.toString()

        if (currentItem.isBought) holder.binding.rbPurchased.isChecked = true
        else holder.binding.rbNotPurchased.isChecked = true

        holder.binding.ivDelete.setOnClickListener{
                shoppingViewModel.delete(currentItem)
            Toast.makeText(holder.binding.root.context,
                "item with id: ${currentItem.id} is deleted",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.binding.ivPlus.setOnClickListener{
            currentItem.amount++
            shoppingViewModel.edit(currentItem)
        }

        holder.binding.ivMinus.setOnClickListener{
            if(currentItem.amount > 0){
                currentItem.amount--
                shoppingViewModel.edit(currentItem)
            }
        }

        holder.binding.rbPurchased.setOnClickListener{
            currentItem.isBought = true
                shoppingViewModel.edit(currentItem)
        }

        holder.binding.rbNotPurchased.setOnClickListener{
            currentItem.isBought = false
            shoppingViewModel.edit(currentItem)
        }
    }

    override fun getItemCount(): Int =items.size

    fun setItems(items: List<ShoppingItem>){
        this.items = items
        notifyDataSetChanged()
    }
}