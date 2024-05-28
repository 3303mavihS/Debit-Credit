package com.example.debitcredit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debitcredit.databaseROOM.TransactionEntity

class RecyclerViewAdapter(
    private val listOfPerson: List<TransactionEntity>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview,parent,false)
        return RecyclerViewItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfPerson.size
    }

    override fun onBindViewHolder(holder: RecyclerViewItemViewHolder, position: Int) {
        val currentItem = listOfPerson[position]
        holder.image.setImageResource(currentItem.image)
        holder.personName.text = currentItem.person
        holder.amount.text = currentItem.amount.toString()
        holder.borrowedFor.text = currentItem.borrowedFor

        holder.itemView.setOnClickListener{
            clickListener.updateTransaction(currentItem)
            Log.i("Update Status","Transaction Update")
        }
        holder.deleteBtn.setOnClickListener{
            clickListener.deleteTransaction(currentItem)
            Log.i("Delete Status","Transaction Deleted")
        }
    }

    /**
     * A View Holder to get the variable for the available views
     */
    class RecyclerViewItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.itemImg)
        val personName : TextView = itemView.findViewById(R.id.pay_to)
        val amount : TextView = itemView.findViewById(R.id.amount)
        val borrowedFor : TextView = itemView.findViewById(R.id.borrowed_for)
        val deleteBtn : ImageView = itemView.findViewById(R.id.deleteBtn)
    }

    interface ClickListener{
        fun updateTransaction(transaction: TransactionEntity)
        fun deleteTransaction(transaction: TransactionEntity)
    }
}