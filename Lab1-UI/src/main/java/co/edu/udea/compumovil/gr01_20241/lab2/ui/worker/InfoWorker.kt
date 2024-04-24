package co.edu.udea.compumovil.gr01_20241.lab2.ui.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail.SnackDetailViewModel

class InfoWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        snackDetailViewModel.updateDetails("Mensaje")
        val msg = "Mensaje"//snackDetailViewModel.infoUiState
        makeStatusNotification(
            snackDetailViewModel.details,
            applicationContext
        )
        TODO("Not yet implemented")
    }

    companion object {
        lateinit var snackDetailViewModel: SnackDetailViewModel

        fun setViewModel(view: SnackDetailViewModel) {
            snackDetailViewModel = view
        }
    }
}