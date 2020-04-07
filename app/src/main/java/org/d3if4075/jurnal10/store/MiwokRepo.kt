package org.d3if4075.jurnal10.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.d3if4075.jurnal10.data.Miwok
import org.d3if4075.jurnal10.database.MiwokDatabase
import org.d3if4075.jurnal10.database.asDatabaseModel
import org.d3if4075.jurnal10.database.asDomainModel
import org.d3if4075.jurnal10.network.MiwokApi
import retrofit2.await

class MiwokRepo(private val database: MiwokDatabase) {

    val miwok: LiveData<List<Miwok>> = Transformations.map(database.miwokDao.getMiwok()) {
        it.asDomainModel()
    }

    suspend fun refreshMiwok() {
        withContext(Dispatchers.IO) {
            val miwok = MiwokApi.retrofitService.showList().await()
            database.miwokDao.insertAll(miwok.asDatabaseModel())
        }
    }
}