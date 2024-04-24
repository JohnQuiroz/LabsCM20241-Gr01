package co.edu.udea.compumovil.gr01_20241.lab2.ui.worker

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkManagerJetsnackRepository(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun getInfo(){
        val infoBuilder = OneTimeWorkRequestBuilder<InfoWorker>()
        workManager.enqueue(infoBuilder.build())
    }
}