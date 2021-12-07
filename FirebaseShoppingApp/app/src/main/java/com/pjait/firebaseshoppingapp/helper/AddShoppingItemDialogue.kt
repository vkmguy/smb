package com.pjait.shopping.ui.shoppingList

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.pjait.firebaseshoppingapp.databinding.DialogAddShoppingItemBinding
import com.pjait.firebaseshoppingapp.model.ShoppingItem

class AddShoppingItemDialogue(context: Context, var addDialogueListener: AddDialogueListener) :
    AppCompatDialog(context) {

    lateinit var binding: DialogAddShoppingItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddShoppingItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val amount = binding.etAmount.text.toString()
            val price = binding.etPrice.text.toString()

            if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
                Toast.makeText(context, "Please enter all information ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = ShoppingItem(name = name, amount = amount.toInt(), price = price.toInt())
            addDialogueListener.onAddButtonClicked(item)
            dismiss()
        }


        binding.btCancel.setOnClickListener {
            cancel()
        }
    }
}
