package com.example.debitcredit.databaseROOM

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactionEntity: TransactionEntity)

    @Delete
    fun delete(transactionEntity: TransactionEntity)

    @Update
    fun update(transactionEntity: TransactionEntity)

    @Query("Select * from TransactionTable")
    fun readAllTransactionDetails() : LiveData<List<TransactionEntity>>

}