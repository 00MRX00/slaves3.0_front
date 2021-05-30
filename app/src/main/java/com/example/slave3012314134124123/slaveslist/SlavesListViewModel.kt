package com.example.slave3012314134124123.slaveslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SlavesListViewModel  @Inject constructor(
    private val repository: UserRepository
): ViewModel() {


    var slavesList = mutableStateOf<List<SlavesListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    lateinit var result: Resource<SlavesList>

    var token2 = mutableStateOf<String>("")

    init {
        loadSlavesPaginated()
    }


    fun loadSlavesPaginated() {
        viewModelScope.launch {
            isLoading.value = true

            result =
                repository.getSlavesList("AccessToken ${token2.value}")
            result =
                repository.getSlavesList("AccessToken ${token2.value}")
            Log.e("SLAVE-LIST", result.message.toString())

            Log.e("SLAVE-LIST SIZE", "size ${result.data?.size}")


            //result =
            //  repository.getSlavesList("AccessToken ${token2.value}")


            Log.e("SLAVE-LIST", result.message.toString())

            Log.e("SLAVE-LIST SIZE", "size ${result.data?.size}")

            when (result) {
                is Resource.Success -> {

                    val slavesEntries = result.data!!.mapIndexed { index, entry ->
                        val fio = entry.fio
                        val photo = entry.photo
                        val profit = entry.profit
                        val job_name = entry.job_name
                        val slaveLvl = entry.slave_level
                        val defLvl = entry.defender_level
                        val id = entry.id
                        val fetter_type = entry.fetter_type
                        val fetter_time = entry.fetter_time
                        val has_fetter = entry.has_fetter
                        val fetter_duration = entry.fetter_duration

                        SlavesListEntry(fio, photo, profit, job_name, slaveLvl, defLvl, id,fetter_type,fetter_time,has_fetter,fetter_duration)
                    }
                    loadError.value = ""
                    isLoading.value = false
                    slavesList.value += slavesEntries

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}