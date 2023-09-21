package com.nameisjayant.mobileassesment.features.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nameisjayant.mobileassesment.R
import com.nameisjayant.mobileassesment.ui.theme.dark_gray_color
import com.nameisjayant.mobileassesment.ui.theme.gilroyBold
import com.nameisjayant.mobileassesment.ui.theme.gilroySemiBold
import com.nameisjayant.mobileassesment.ui.theme.gray_001

/*
title for our recyclerview , title and file number will change on the basis of section type
 */

@Composable
fun TitleRow(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string._2d_layout_adaptation),
    total: String ,
    isShow: Boolean = true,
    onValueUpdate: (Boolean) -> Unit
) {

    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .toggleable(
                value = isShow,
                onValueChange = {onValueUpdate(!isShow)},
                role = Role.Button,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = gilroyBold,
                    color = Color.Black
                ),
                modifier = Modifier.align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "$total ${stringResource(id = R.string.files)}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = gilroySemiBold,
                    color = dark_gray_color
                ),
                modifier = Modifier
                    .drawBehind {
                        drawRoundRect(
                            color = gray_001,
                            cornerRadius = CornerRadius(x = 3.dp.toPx(), y = 3.dp.toPx())
                        )
                    }
                    .align(CenterVertically)
                    .padding(horizontal = 5.dp, vertical = 2.dp),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = if (isShow) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
        )
    }


}