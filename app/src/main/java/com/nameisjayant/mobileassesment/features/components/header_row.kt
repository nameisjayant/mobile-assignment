package com.nameisjayant.mobileassesment.features.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nameisjayant.mobileassesment.R
import com.nameisjayant.mobileassesment.ui.theme.gilroyRegular
import com.nameisjayant.mobileassesment.ui.theme.gilroySemiBold


/*
top header screen
 */

@Composable
fun HeaderRow(
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 22.dp, top = 15.dp, bottom = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.design_layouts),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Black,
                    fontFamily = gilroySemiBold
                )
            )
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = stringResource(R.string.client),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Black,
                        fontFamily = gilroyRegular
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.bridgestone),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Black,
                        fontFamily = gilroySemiBold
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.job_id),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontFamily = gilroyRegular
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(R.string.brid1337),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontFamily = gilroySemiBold
                    )
                )
            }

        }
    }

}