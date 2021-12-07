package com.pjait.firebaseshoppingapp.helper

import com.google.firebase.database.FirebaseDatabase

object Constants {
    val sharedDbRef = FirebaseDatabase.getInstance().getReference("shared/")
    val dbRef = FirebaseDatabase.getInstance()
}
