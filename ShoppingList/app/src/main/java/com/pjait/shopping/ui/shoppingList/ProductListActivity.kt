package com.pjait.shopping.ui.shoppingList

import android.app.UiModeManager.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjait.shopping.R
import com.pjait.shopping.adapter.ShoppingItemAdapter
import com.pjait.shopping.data.db.entity.ShoppingItem
import com.pjait.shopping.databinding.ActivityShoppingBinding
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "SHOPPING LIST"
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val viewModel = ShoppingViewModel(application)
        val shoppingAdapter = ShoppingItemAdapter(viewModel)

        viewModel.allItem.observe(this,
            Observer {
                    items -> items?.let {
                shoppingAdapter.setItems(it)
                    }
            })

        binding.rvShoppingItems.adapter = shoppingAdapter

        binding.fab.setOnClickListener{
            AddShoppingItemDialogue(this,
            object: AddDialogueListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.insert(item)
                }
            }).show()
        }


    }
}
