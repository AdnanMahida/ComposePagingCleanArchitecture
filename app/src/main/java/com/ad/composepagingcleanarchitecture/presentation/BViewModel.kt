package com.ad.composepagingcleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ad.composepagingcleanarchitecture.data.local.BEntity
import com.ad.composepagingcleanarchitecture.data.remote.toB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BViewModel @Inject constructor(
    pager: Pager<Int, BEntity>
) : ViewModel() {

    val bPagingFlow = pager.flow.map { value: PagingData<BEntity> ->
        value.map { it.toB() }
    }.cachedIn(viewModelScope)


}