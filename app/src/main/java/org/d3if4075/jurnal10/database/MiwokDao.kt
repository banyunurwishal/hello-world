package org.d3if4075.jurnal10.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

fun getInstance(context: Context) : MiwokDatabase {
    synchronized(MiwokDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MiwokDatabase::class.java,
                "miwok_database").build()
        }
    }
    return INSTANCE
}

@Dao
interface MiwokDao {

    @Query("SELECT * FROM miwok")
    fun getMiwok(): LiveData<List<MiwokDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(miwok: List<MiwokDatabaseModel>)

}

@Database(entities = [MiwokDatabaseModel::class], version = 1, exportSchema = false)
abstract class MiwokDatabase : RoomDatabase() {
    abstract val miwokDao: MiwokDao
}

private lateinit var INSTANCE: MiwokDatabase