package co.edu.udea.compumovil.gr01_20241.lab2.ui.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail.SnackDetailViewModel
import kotlinx.coroutines.withContext

class InfoWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        makeStatusNotification(
            "Getting information from de API",
            applicationContext
        )
        return try {
            snackDetailViewModel.getInfo()
            Result.success()
        } catch (e: Exception) {
            Log.e("InfoWorker", "Error en la carga de información")
            Result.failure()
        }
    }

    companion object {
        lateinit var snackDetailViewModel: SnackDetailViewModel

        fun setViewModel(view: SnackDetailViewModel) {
            snackDetailViewModel = view
        }
    }
}