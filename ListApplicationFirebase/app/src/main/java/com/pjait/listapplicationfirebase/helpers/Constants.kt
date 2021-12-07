package com.pjait.listapplicationfirebase.helpers

import com.google.firebase.database.FirebaseDatabase

object Constants {
    val commonDbRef = FirebaseDatabase.getInstance().getReference("common")
    val usersDbRef = FirebaseDatabase.getInstance().getReference("users")
}
