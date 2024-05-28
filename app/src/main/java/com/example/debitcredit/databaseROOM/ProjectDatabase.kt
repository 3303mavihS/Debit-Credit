package com.example.debitcredit.databaseROOM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ProjectDatabase: RoomDatabase() {

    abstract fun getDatabaseDAO() : DatabaseDAO

    companion object{
        private const val DB_NAME = "TransactionDatabase.db"

        private var instance: ProjectDatabase?= null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProjectDatabase::class.java,
            DB_NAME
        ).build()
    }

}