package com.mite.djigibao.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.domain.firebase.FirestoreSync
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fireSync: FirestoreSync
) : ViewModel() {
    init {
        syncFirestore()
    }

    private fun syncFirestore() = viewModelScope.launch(Dispatchers.IO) {
        fireSync.sync()
    }
}