package com.example.debitcredit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.debitcredit.databaseROOM.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class BridgeViewModel(
    private val dataRepo: DataRepository
) : ViewModel() {
    /**
     * ViewModel is Bridge between UI and DataRepository
     * and pass the data to ui
     * therefore it require livedata
     */
    //private val listOfDetailsAsLiveData = MutableLiveData<List<TransactionEntity>>()

    /**
     * init the ViewModel
     */
    init {
//        getAllDataFromDataRepo()
    }

    /**
     * getAllData() from repository
     */
//    private fun getAllDataFromDataRepo(){
//        val allTransactions = dataRepo.getAllTransactionDetails()
//        listOfDetailsAsLiveData.value = allTransactions
//    }

    /**
     * addTransactionInList() to repository
     */
//    fun addTransactionInList(detail: TransactionDetail){
//        dataRepo.addTransactionToList(detail)
//    }


    /**
     * This part is getting the repo as parameter
     * to connect to the ui
     * so it will also require the functions to operate on repo
     */
    fun getAllDataFromDatabaseRepo() = dataRepo.getAllTransactions()

    fun insert(transaction: TransactionEntity){
        viewModelScope.launch(IO){
            dataRepo.insertTransaction(transaction)
        }
    }
    fun delete(transaction: TransactionEntity){
        viewModelScope.launch(IO) {
            dataRepo.deleteTransaction(transaction)
        }
    }
    fun update(transaction: TransactionEntity){
        viewModelScope.launch(IO) {
            dataRepo.updateTransaction(transaction)
        }
    }

}