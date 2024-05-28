package com.example.debitcredit

import com.example.debitcredit.databaseROOM.DatabaseDAO
import com.example.debitcredit.databaseROOM.TransactionEntity

class DataRepository(
    private val dao : DatabaseDAO
) {
    /**
     * This class is a part of MVVM architecture for android
     * This class will hold all the data for the project.
     * And it is repository
     */
//    private  val listOfDetails = mutableListOf<TransactionDetail>()
    /**
     * function to get all the data
     */
//    fun getAllTransactionDetails() = listOfDetails
    /**
     * add transaction to list
     */
//    fun addTransactionToList(detail: TransactionDetail) = listOfDetails.add(detail)

    /**
     * Here we are making this data repository to
     * hold data from database
     * it will also help add,delete,update and getAll() the data
     * so it will require to get an object of DatabaseDAO as a parameter for this data repository
     * to connect the DAO to repo
     */
    fun insertTransaction(transaction:TransactionEntity){
        dao.insert(transaction)
    }
    fun deleteTransaction(transaction: TransactionEntity){
        dao.delete(transaction)
    }
    fun updateTransaction(transaction: TransactionEntity){
        dao.update(transaction)
    }
    fun getAllTransactions() = dao.readAllTransactionDetails()
}