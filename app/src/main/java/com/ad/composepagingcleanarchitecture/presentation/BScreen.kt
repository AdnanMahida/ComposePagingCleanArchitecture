package com.ad.composepagingcleanarchitecture.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ad.composepagingcleanarchitecture.domain.B

@Composable
fun BScreen(
    b: LazyPagingItems<B>
) {
    val context = LocalContext.current

    LaunchedEffect(
        key1 = b.loadState
    ) {
        if (b.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (b.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    Box(modifier = Modifier.fillMaxSize())
    {
        if (b.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = b.itemCount,
                    key = b.itemKey(),
                    contentType = b.itemContentType(
                    )
                ) { index ->
                    val item = b[index]
                    if (item != null) {
                        BItems(b = item, modifier = Modifier.fillMaxWidth())
                    }
                }

                item {
                    if (b.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }

}