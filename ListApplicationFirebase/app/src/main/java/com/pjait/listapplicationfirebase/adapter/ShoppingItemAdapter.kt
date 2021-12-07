package com.pjait.listapplicationfirebase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.pjait.listapplicationfirebase.databinding.ShoppingItemBinding
import com.pjait.listapplicationfirebase.model.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingItemAdapter(
    private val shoppingList: ArrayList<ShoppingItem>,
    private val ref: DatabaseReference
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    private var items = emptyList<ShoppingItem>()

//    init {
//        ref.addChildEventListener(object : ChildEventListener{
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                CoroutineScope(IO).launch {
//                    shoppingList.add(snapshot.value as ShoppingItem)
//                    withContext(Main){
//                        notifyDataSetChanged()
//                    }
//                }
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                CoroutineScope(IO).launch {
//                    shoppingList.remove(snapshot.value as ShoppingItem)
//                    shoppingList.add(snapshot.value as ShoppingItem)
//                    withContext(Main){
//                        notifyDataSetChanged()
//                    }
//                }
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                CoroutineScope(IO).launch {
//                    for(dataSnapshot in snapshot.children){
//                        shoppingList.remove(snapshot.value as ShoppingItem)
//                    }
//                    withContext(Main){
//                        notifyDataSetChanged()
//                    }
//                }
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

    class ShoppingViewHolder(val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ShoppingItemBinding.inflate(LayoutInflater.from(parent.context))
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.tvName.text = currentItem.name
        holder.binding.tvAmount.text = currentItem.amount.toString()
        holder.binding.tvPrice.text = currentItem.price.toString()
        holder.binding.btDelete.setOnClickListener {
            CoroutineScope(IO).launch {
                withContext(Main){
                    ref.removeValue()
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}
