package com.pjait.listapplicationfirebase.shoppingList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pjait.listapplicationfirebase.databinding.ActivityShoppingBinding
import com.pjait.listapplicationfirebase.adapter.ShoppingItemAdapter
import com.pjait.listapplicationfirebase.helpers.Constants
import com.pjait.listapplicationfirebase.model.ShoppingItem
import com.pjait.shopping.ui.shoppingList.AddDialogueListener
import com.pjait.shopping.ui.shoppingList.AddShoppingItemDialogue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityShoppingBinding
    private val commonDbRef = Constants.commonDbRef
    private lateinit var list: ArrayList<ShoppingItem>

    //val usersDbRef = Constants.usersDbRef
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        list = arrayListOf()
//        getShoppingData()
        val shoppingAdapter = ShoppingItemAdapter(list, commonDbRef)
        binding.rvShoppingItems.adapter = shoppingAdapter

        commonDbRef.setValue("Hello, World!")
        binding.fab.setOnClickListener {
            AddShoppingItemDialogue(this,
                object : AddDialogueListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.Main){
                                commonDbRef.push().setValue(item)
                            }
                        }
                    }
                }).show()
        }
    }

//    private fun getShoppingData() {
//        commonDbRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (shoppingItems in snapshot.children) {
//                        Log.d("test", shoppingItems.value.toString())
//                        val shoppingItem = shoppingItems.getValue(ShoppingItem::class.java)
//                        list.add(shoppingItem!!)
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("error-database", error.message)
//            }
//        })
//    }
}
