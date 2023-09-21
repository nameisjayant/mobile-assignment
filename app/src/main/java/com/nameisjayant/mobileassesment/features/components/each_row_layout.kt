package com.nameisjayant.mobileassesment.features.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nameisjayant.mobileassesment.R
import com.nameisjayant.mobileassesment.base.getDate
import com.nameisjayant.mobileassesment.data.models.DataResponse
import com.nameisjayant.mobileassesment.ui.theme.aqua_color
import com.nameisjayant.mobileassesment.ui.theme.dark_gray_color
import com.nameisjayant.mobileassesment.ui.theme.gilroyBold
import com.nameisjayant.mobileassesment.ui.theme.gilroySemiBold
import com.nameisjayant.mobileassesment.ui.theme.gray_color
import com.nameisjayant.mobileassesment.ui.theme.light_gray_color
import com.nameisjayant.mobileassesment.ui.theme.light_red
import com.nameisjayant.mobileassesment.ui.theme.red_color
import com.nameisjayant.mobileassesment.ui.theme.robotoRegular
import kotlinx.coroutines.Job


/*
Each Row layout for our recyclerview
 */

@Composable
fun EachRowLayout(
    modifier: Modifier = Modifier,
    data: DataResponse,
    progressValue:Int,
    cancelDownload:Boolean,
    onCancelDownloadUpdate:(Boolean)->Unit,
    job: Job,
    @DrawableRes icon: Int,
    onMenuClick: () -> Unit = {}
) {
    var isDownloadShow by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.White, RoundedCornerShape(7.dp))
            .border(1.dp, light_gray_color, RoundedCornerShape(7.dp))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .width(24.dp)
                        .height(30.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = data.name ?: "-", style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = gilroySemiBold, color = Color.Black
                        ), maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row {
                        Text(text = "V${data.version ?: "0"}",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontFamily = gilroyBold, color = red_color
                            ),
                            modifier = Modifier
                                .drawBehind {
                                    drawRoundRect(
                                        color = light_red,
                                        cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                                    )
                                }
                                .padding(2.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = getDate(data.uploadedAt ?: "2023-04-24T09:03:24.571296+05:30"),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontFamily = robotoRegular, color = gray_color
                            ),
                            modifier = Modifier.align(CenterVertically)
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row {
                if (data.isDownloaded == true)
                    Icon(
                        painter = painterResource(id = R.drawable.done), contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .align(CenterVertically), tint = Color.Unspecified
                    )
                if (data.isDownloading!! && !cancelDownload)
                    Box(
                        modifier = Modifier
                            .align(CenterVertically)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {
                                // on clicking on cancel button , cancel the coroutine scope
                                onCancelDownloadUpdate(true)
                                data.isDownloading = false
                                job.cancel()
                            }
                    ) {
                        CircularProgressIndicator(
                            progress = progressValue / 100f,
                            color = aqua_color,
                            trackColor = aqua_color.copy(alpha = 0.3f),
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = dark_gray_color,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .size(12.dp)
                        )
                    }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = { isDownloadShow = true },
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dots),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
                DropdownMenu(expanded = isDownloadShow,
                    onDismissRequest = { isDownloadShow = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.download),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontFamily = gilroySemiBold, color =
                                    if (data.isDownloaded == true || data.isDownloading == true) gray_color else Color.Black
                                )
                            )
                        },
                        onClick = {
                            if (data.isDownloaded == false && data.isDownloading == false) {
                                isDownloadShow = false
                                onMenuClick()
                                onCancelDownloadUpdate(false)
                            }
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_file_download_24),
                                contentDescription = null,
                                tint = if (data.isDownloaded == true || data.isDownloading == true) gray_color else Color.Black
                            )
                        },
                    )
                }
            }
        }
    }

}