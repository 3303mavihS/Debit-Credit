package com.example.debitcredit.databaseROOM

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TransactionTable")
data class TransactionEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val image : Int,
    val person : String,
    val amount : Int,
    val borrowedFor : String
)