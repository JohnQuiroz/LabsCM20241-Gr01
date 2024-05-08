package co.edu.udea.compumovil.gr01_20241.lab2.ui.snackdetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr01_20241.lab2.network.InfoApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SnackUiState {
    data class Success(val state: String) : SnackUiState
    object Error : SnackUiState
    object Loading : SnackUiState
}

class SnackDetailViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SnackDetailUIState())
    val uiState: StateFlow<SnackDetailUIState> = _uiState.asStateFlow()

    var details by mutableStateOf("")
        private set

    fun updateDetails(entered: String) {
        details = entered
    }

    var ingredients by mutableStateOf("")
        private set

    fun updateIngredients(entered: String) {
        ingredients = entered
    }

    var infoUiState: SnackUiState by mutableStateOf(SnackUiState.Loading)
        private set

    /*init {
        getInfo()
    }*/

    fun getInfo() {
        /*infoUiState = "Set the Mars API status response here!"
        Log.i("SnackDetailViewModel", infoUiState)*/
        viewModelScope.launch {
            infoUiState = try {
                val listResult = InfoApi.retrofitService.getDetail()
                Log.i("SnackDetailViewModel", listResult.detailPlaceholder)
                updateDetails(listResult.detailPlaceholder)
                updateIngredients(listResult.ingredientsList)
                SnackUiState.Success("SUCCESS")
            } catch (e: IOException) {
                SnackUiState.Error
            }
            /*val listResult = InfoApi.retrofitService.getDetail()
            infoUiState = listResult
            Log.i("SnackDetailViewModel", infoUiState)*/
        }
    }
}