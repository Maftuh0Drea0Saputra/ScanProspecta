package com.capstone.scanprospecta.data.response

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Message(
    val text: String? = null,
    val name: String? = null,
    val timestamp: Long? = null
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}