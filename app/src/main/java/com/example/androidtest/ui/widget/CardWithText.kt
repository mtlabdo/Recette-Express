package com.example.androidtest.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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

@Composable
fun CardWithTextView(
    modifier: Modifier = Modifier,
    label: String,
    icon: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 10.dp)
            )

            Text(
                text = label,
                color = Color.Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview
@Composable
fun CardWithTextViewPreview() {
    CardWithTextView(
        modifier = Modifier.fillMaxWidth(),
        label = "Afficher le detail",
        onClick = {  },
        icon = android.R.drawable.ic_menu_more
    )
}