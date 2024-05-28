package com.example.debitcredit

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debitcredit.RecyclerViewAdapter.ClickListener
import com.example.debitcredit.databaseROOM.ProjectDatabase
import com.example.debitcredit.databaseROOM.TransactionEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ClickListener {

    /**
     * declaration of the element views
     */
    private lateinit var activityRecyclerView: RecyclerView
    private lateinit var floatBtn: FloatingActionButton
    private lateinit var personText: EditText
    private lateinit var amountText: EditText
    private lateinit var reasonText: EditText
    private lateinit var catRadio: RadioGroup
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var addBtn: Button

    /**
     * recyclerView Adapter
     */
    private lateinit var rvAdapter: RecyclerViewAdapter

    /**
     * MVVM architecture declaration
     */
    private lateinit var repository: DataRepository
    private lateinit var viewModelFactory: MVVMViewModelFactory
    private lateinit var viewModel: BridgeViewModel
    private lateinit var projectDB: ProjectDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //floating button
        floatBtn = findViewById(R.id.floatButton)
        //initialization of the view variables
        activityRecyclerView = findViewById(R.id.listOfRecentlyAdded)
        //setting layoutManager for activity RecyclerView
        activityRecyclerView.layoutManager = LinearLayoutManager(this)

        //database initialization
        projectDB = ProjectDatabase(this)
        //initialization of the mvvm architecture
        repository = DataRepository(projectDB.getDatabaseDAO())
        viewModelFactory = MVVMViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[BridgeViewModel::class.java]

        //observing livedata coming from viewModel
        viewModel.getAllDataFromDatabaseRepo().observe(this){
            Log.i("Database Retrieve",it.toString())
            //passing data to recyclerView Adapter
            rvAdapter = RecyclerViewAdapter(it,this)
            //setting recyclerView Adapter
            activityRecyclerView.adapter = rvAdapter
        }

        floatBtn.setOnClickListener{
            showDialogBox(true)
        }
    }

    private fun showDialogBox(comingFromFAB : Boolean){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_box)
        dialog.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //assigning the view variables
        personText = dialog.findViewById(R.id.name)
        amountText = dialog.findViewById(R.id.amount)
        reasonText = dialog.findViewById(R.id.reason)
        catRadio = dialog.findViewById(R.id.radioGroup)
        addBtn = dialog.findViewById(R.id.add_btn)
        option1 = dialog.findViewById(R.id.radioOption1)
        option2 = dialog.findViewById(R.id.radioOption2)

        //option selected
        var option : Int = R.drawable.option_circle
        option1.setOnCheckedChangeListener{ _, _ ->
            option = R.drawable.circle
        }
        option2.setOnCheckedChangeListener{ _, _ ->
            option = R.drawable.check
        }

        //Do something for the views
        addBtn.setOnClickListener{

            //getting the data and then storing it in the mutable List
            val personNameEdit = personText.text.toString()
            val amountEdit : String = amountText.text.toString()
            val reasonEdit = reasonText.text.toString()
            val catImg : Int =  option

            if(personNameEdit.isEmpty()||amountEdit.isEmpty()||reasonEdit.isEmpty()){
                Toast.makeText(this,"Fields are Empty.",Toast.LENGTH_SHORT).show()
            }
            else{
                val amt:Int = amountEdit.toInt()
                val dataItem = TransactionEntity(
                    image = catImg,
                    person = personNameEdit,
                    amount = amt,
                    borrowedFor = reasonEdit
                )
                if(comingFromFAB){
                    Log.i("Transaction Inserted", dataItem.person + dataItem.amount + dataItem.borrowedFor + dataItem.image)
                    viewModel.insert(dataItem)
                    Log.i("Status to Database", "Added to Database")
                }else{
                    Log.i("Transaction Updated", dataItem.person + dataItem.amount + dataItem.borrowedFor)
                    viewModel.update(dataItem)
                    Log.i("Status to Database", "Updated in Database")
                }
                //rvAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }

        //show dialog
        dialog.show()
    }

    override fun updateTransaction(transaction: TransactionEntity) {
        showDialogBox(false)
    }

    override fun deleteTransaction(transaction: TransactionEntity) {
        viewModel.delete(transaction)
    }
}