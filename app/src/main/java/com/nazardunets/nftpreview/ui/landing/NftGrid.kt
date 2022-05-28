package com.nazardunets.nftpreview.ui.landing

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun NftGrid(
    modifier: Modifier = Modifier,
    allowOverflow: Boolean = true
) {
    val context = LocalContext.current
    val gridData = remember { getGridData(context) }

    NftGridContainer(
        modifier = modifier,
        allowOverflow = allowOverflow
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .rotate(-5f)
        ) {
            gridData.forEachIndexed { index, items ->
                NftRow(itemsRes = items, speedFactor = SpeedFactors[index])
            }
        }
    }
}

@Composable
private fun NftGridContainer(
    modifier: Modifier,
    allowOverflow: Boolean,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.fillMaxWidth(),
        content = content
    ) { msrbls, cnstr ->
        val actualMaxWidth = if (allowOverflow) cnstr.maxWidth + WIDTH_OVERFLOW else cnstr.maxWidth

        val placeable = msrbls[0].measure(Constraints(maxWidth = actualMaxWidth, maxHeight = cnstr.maxHeight))

        layout(width = actualMaxWidth, height = placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}

@Composable
private fun NftRow(
    itemsRes: List<Int>,
    speedFactor: Float
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val scrollProgress by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (BASE_ANIM_DURATION * speedFactor).roundToInt(),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        val density = LocalDensity.current
        val maxScrollPosition = remember {
            with(density) {
                ImageSize.toPx() * ITEMS_PER_ROW - constraints.maxWidth
            }
        }

        val scrollState = rememberScrollState()

        LaunchedEffect(scrollProgress) {
            scrollState.scrollTo((scrollProgress * maxScrollPosition).roundToInt())
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(state = scrollState, enabled = false)
        ) {
            itemsRes.forEach {
                Image(
                    modifier = Modifier.size(130.dp),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

private const val ROWS_COUNT = 4
private const val ITEMS_PER_ROW = 12
private const val BASE_ANIM_DURATION = 12_000
private const val WIDTH_OVERFLOW = 100

private val ImageSize = 130.dp
private val SpeedFactors = floatArrayOf(1f, .7f, .9f, 1.1f)

private fun getGridData(context: Context): List<List<Int>> {
    return List(ROWS_COUNT) { rowIndex ->
        List(ITEMS_PER_ROW) { itemIndex ->
            val nftNumber = rowIndex * ITEMS_PER_ROW + itemIndex + 1
            getNftIdentifier(number = nftNumber, context)
        }
    }
}

private fun getNftIdentifier(number: Int, context: Context): Int {
    return context.resources.getIdentifier("nft$number", "mipmap", context.packageName)
}

@Preview
@Composable
private fun NftGridPreview() {
    NftGrid()
}
