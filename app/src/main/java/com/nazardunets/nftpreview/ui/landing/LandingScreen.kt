package com.nazardunets.nftpreview.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen() {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            NftGrid(
                allowHorizontalOverflow = true
            )
            VerticalFadeGradient(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                direction = FadeDirection.DOWN
            )
            VerticalFadeGradient(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                direction = FadeDirection.UP
            )
        }

        val horizontalPaddingModifier = Modifier.padding(horizontal = 24.dp)

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = horizontalPaddingModifier,
            text = "Art with NFTs",
            style = TitleTextStyle
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = horizontalPaddingModifier,
            text = "Check out this raffle for you guys only! New coin minted, show some love.",
            style = SubtitleTextStyle
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            modifier = horizontalPaddingModifier,
            text = "Discover"
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(ActionButtonHeight)
            .clip(ActionButtonShape)
            .background(ActionButtonColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 48.dp)
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = ActionButtonTextStyle
        )
    }
}

@Composable
private fun VerticalFadeGradient(
    modifier: Modifier = Modifier,
    direction: FadeDirection
) {
    Box(
        modifier = modifier
            .height(FadeGradientHeight)
            .fillMaxWidth()
            .background(
                when (direction) {
                    FadeDirection.UP ->
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            .9f to MaterialTheme.colors.background
                        )
                    FadeDirection.DOWN ->
                        Brush.verticalGradient(
                            .1f to MaterialTheme.colors.background,
                            1f to Color.Transparent
                        )
                }
            )
    )
}

private enum class FadeDirection {
    UP, DOWN
}

private val TitleTextStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
private val SubtitleTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)

private val FadeGradientHeight = 150.dp

private val ActionButtonHeight = 54.dp
private val ActionButtonShape = RoundedCornerShape(8.dp)
private val ActionButtonColor = Color(0xFF1821D3)
private val ActionButtonTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
