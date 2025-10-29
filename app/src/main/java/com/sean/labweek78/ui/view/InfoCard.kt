package com.sean.labweek78.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sean.labweek78.R

@Composable
fun infoCard(
    title: String,
    imageId: Int,
    value: String
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff555a6e)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = "image",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = title,
                color = Color(0xff9095a0),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun infoCardPreview() {
    infoCard(
        title = "Feels Like",
        imageId = R.drawable.icon_feels_like,
        value = "20%"
    )
}