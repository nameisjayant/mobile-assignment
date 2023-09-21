package com.nameisjayant.mobileassesment.features.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nameisjayant.mobileassesment.features.components.EachRowLayout
import com.nameisjayant.mobileassesment.features.components.HeaderRow
import com.nameisjayant.mobileassesment.features.components.TitleRow
import com.nameisjayant.mobileassesment.features.ui.viewmodel.MainViewModel
import com.nameisjayant.mobileassesment.R
import com.nameisjayant.mobileassesment.base.ConnectionState
import com.nameisjayant.mobileassesment.base.downloadAndStoreFile
import com.nameisjayant.mobileassesment.base.rememberConnectivityState
import com.nameisjayant.mobileassesment.data.models.DataResponse
import com.nameisjayant.mobileassesment.ui.theme.background_color
import com.nameisjayant.mobileassesment.utils.PROD
import com.nameisjayant.mobileassesment.utils.THREE_D
import com.nameisjayant.mobileassesment.utils.TWO_D
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val response by viewModel.responseEventFlow.collectAsStateWithLifecycle()
    val sectionCollapsed = remember { mutableStateMapOf<String, Boolean>() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    /*
    This will check the internet connection
     */
    val connectivity = rememberConnectivityState()
    val isConnected = connectivity.value == ConnectionState.Available

    LaunchedEffect(key1 = isConnected) {
        if (isConnected)
            viewModel.onEvent()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background_color)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            HeaderRow()
            if (response.data != null)
                if (response.data?.data?.isNotEmpty() == true)
                    LazyColumn(
                        state = state
                    ) {
                        /*
                        grouping lists on basis of section type
                         */
                        val groupItems = response.data?.data?.groupBy { it.section }
                        groupItems?.forEach { (section, item) ->
                            item {
                                TitleRow(
                                    isShow = sectionCollapsed[section] == true,
                                    title = when (section) {
                                        TWO_D -> stringResource(id = R.string._2d_layout_adaptation)
                                        PROD -> stringResource(R.string.production_files_artworks)
                                        THREE_D -> stringResource(R.string._3d_layout_adaptation)
                                        else -> stringResource(R.string.other_content)
                                    },
                                    total = "${item.size}"
                                ) {
                                    sectionCollapsed[section!!] = it
                                }
                            }

                            if (sectionCollapsed[section!!] == true)
                                items(item, key = {
                                    it.id ?: it.hashCode()
                                }) {
                                    var progressValue by rememberSaveable { mutableIntStateOf(0) }
                                    var job by remember { mutableStateOf<Job?>(null) }
                                    var cancelDownload by remember { mutableStateOf(false) }
                                    EachRowLayout(
                                        data = it,
                                        icon = when (section) {
                                            TWO_D -> R.drawable.two_d_icon
                                            PROD -> R.drawable.one
                                            THREE_D -> R.drawable.three_d_icon
                                            else -> R.drawable.two_d_icon
                                        },
                                        progressValue = progressValue,
                                        job = job ?: Job(),
                                        cancelDownload = cancelDownload,
                                        onCancelDownloadUpdate = { value ->
                                            cancelDownload = value
                                        }
                                    ) {
                                        /*
                                        On Button clicks , start downloading process
                                         */
                                        if (!it.isDownloading!!) {
                                            it.isDownloading = true
                                            job = scope.launch {
                                                downloadItem(it, context = context) { pro ->
                                                    progressValue = pro
                                                }
                                            }
                                        } else {
                                            it.progress = 0
                                            job?.cancel()

                                        }
                                    }
                                }

                        }
                    }

            if (response.isLoading)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            if (response.error.isNotEmpty())
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = response.error)
                }
        }

    }

}

/*
after completing from 0 to 100 with delay of 30 secs , it will download the image/pdf and stores in download folder
 */

suspend fun downloadItem(data: DataResponse, context: Context, progress: (Int) -> Unit) {
    for (i in 0..100) {
        delay(30)
        withContext(Dispatchers.Main) {
            data.progress = i
            progress(i)
        }
    }
    downloadAndStoreFile(
        context = context,
        fileUrl = data.file ?: "",
        fileName = data.name ?: "File"
    )
    progress(100)
    data.progress = 100
    data.isDownloading = false
    data.isDownloaded = true
}

