package cl.malditosnakamas.briska.registro.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.malditosnakamas.briska.registro.domain.RegistrarUsuarioUseCase
import cl.malditosnakamas.briska.registro.domain.RegistroUsuario
import kotlinx.coroutines.launch
import java.lang.Exception

class RegistroUsuarioViewModel(
    private val registroUsuarioUseCase: RegistrarUsuarioUseCase
) : ViewModel() {

    private val liveData = MutableLiveData<RegistroUiState>()

    fun getLiveData() = liveData

    fun registrarUsuario(registroUsuario: RegistroUsuario) {
        liveData.postValue(RegistroUiState.LoadingRegistroUiState)
        viewModelScope.launch {
            try {
                val result = registroUsuarioUseCase.excecute(registroUsuario)
                handleResult(result)
            } catch (exception: Exception) {
                handleError(exception)
            }
        }
    }

    private fun handleError(exception: Exception) {
        liveData.postValue(RegistroUiState.ErrorRegistroUiState(exception))
    }

    private fun handleResult(result: Boolean) {
        if (result) {
            liveData.postValue(RegistroUiState.SuccessRegistroUiState)
        } else {
            liveData.postValue(RegistroUiState.InvalidEmailRegistroUiState)
        }
    }


}