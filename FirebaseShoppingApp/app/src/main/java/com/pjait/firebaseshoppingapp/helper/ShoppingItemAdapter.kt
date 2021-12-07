package com.pjait.firebaseshoppingapp.helper

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.pjait.firebaseshoppingapp.databinding.ShoppingItemBinding
import com.pjait.firebaseshoppingapp.model.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingItemAdapter(
    private val context: Context,
    val shoppingList: ArrayList<ShoppingItem>,
    val sharedRef: DatabaseReference,
    private val userRef: DatabaseReference
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    init {
        sharedRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.add(product!!)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.remove(product)
                    shoppingList.add(product!!)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.remove(product)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        userRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.add(product!!)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.remove(product)
                    shoppingList.add(product!!)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(IO).launch {
                    val product = snapshot.getValue(ShoppingItem::class.java)
                    shoppingList.remove(product)
                    withContext(Main) {
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    class ShoppingViewHolder(val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ShoppingItemBinding.inflate(LayoutInflater.from(parent.context))
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        holder.binding.tvName.text = currentItem.name
        holder.binding.tvAmount.text = currentItem.amount.toString()
        holder.binding.tvPrice.text = currentItem.price.toString()
        holder.binding.btDelete.setOnClickListener {
            Log.d("tester", "name is ${currentItem.name})")
            sharedRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    CoroutineScope(IO).launch {
                        for (data in snapshot.children) {
                            val product = data.getValue(ShoppingItem::class.java)
                            if (product!!.name == currentItem.name) {
                                sharedRef.child(data.key!!).removeValue()
                            }
                        }
                        withContext(Main) {
                            notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    CoroutineScope(IO).launch {
                        for (data in snapshot.children) {
                            val product = data.getValue(ShoppingItem::class.java)
                            if (product!!.name == currentItem.name) {
                                userRef.child(data.key!!).removeValue()
                            }
                        }
                        withContext(Main) {
                            notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun getItemCount(): Int = shoppingList.size

}
