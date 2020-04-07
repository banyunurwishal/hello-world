package org.d3if4075.jurnal10.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.d3if4075.jurnal10.database.getInstance
import org.d3if4075.jurnal10.store.MiwokRepo
import retrofit2.HttpException

class RefreshData(
    appContext: Context, params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = MiwokRepo(database)

        return try {
            repository.refreshMiwok()
            Result.success()
        } catch (execption: HttpException) {
            Result.retry()
        }
    }
}