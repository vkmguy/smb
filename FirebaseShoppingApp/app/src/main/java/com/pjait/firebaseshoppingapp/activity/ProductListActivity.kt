package com.pjait.firebaseshoppingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pjait.firebaseshoppingapp.databinding.ActivityShoppingBinding
import com.pjait.firebaseshoppingapp.helper.Constants
import com.pjait.firebaseshoppingapp.helper.ShoppingItemAdapter
import com.pjait.firebaseshoppingapp.model.ShoppingItem
import com.pjait.shopping.ui.shoppingList.AddDialogueListener
import com.pjait.shopping.ui.shoppingList.AddShoppingItemDialogue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityShoppingBinding
    private val sharedDbRef = Constants.sharedDbRef
    private lateinit var commonList: ArrayList<ShoppingItem>
    lateinit var shoppingAdapter: ShoppingItemAdapter
    private val fUser = FirebaseAuth.getInstance().currentUser
    private val dbRef = Constants.dbRef
    private val authUserDbRef = dbRef.getReference("users/"+fUser!!.uid)
    private var isShared: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.setHasFixedSize(true)
        binding.rvShoppingItems.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

//        authUserDbRef.push().setValue("Hello World!!")

        commonList = arrayListOf()
        shoppingAdapter = ShoppingItemAdapter(this, commonList, sharedDbRef, authUserDbRef)
        binding.rvShoppingItems.adapter = shoppingAdapter
        binding.fab.setOnClickListener {
            AddShoppingItemDialogue(this,
                object : AddDialogueListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (isShared){
                                sharedDbRef.push().setValue(item)
                            }else{
                                authUserDbRef.push().setValue(item)
                            }
                        }
                    }
                }).show()
        }
        binding.tgShared.setOnClickListener{
            isShared = binding.tgShared.isChecked
        }
    }
}
