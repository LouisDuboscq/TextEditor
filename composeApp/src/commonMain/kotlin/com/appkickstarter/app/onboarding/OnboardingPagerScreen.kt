package com.appkickstarter.app.onboarding

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import appkickstarter.composeapp.generated.resources.*
import appkickstarter.composeapp.generated.resources.Res
import appkickstarter.composeapp.generated.resources.ic_cyclone
import com.appkickstarter.app.designsystem.Heading
import com.appkickstarter.app.designsystem.RoundedButton
import com.appkickstarter.app.designsystem.SimpleLabel
import com.appkickstarter.app.theme.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.absoluteValue
import kotlin.math.sign

internal fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}
 
@Composable
fun OnboardingScreenContent(
    requestContinue: () -> Unit
) {
    val allSwipeDatas = listOf(
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_1_title,
               subtitleText = Res.string.feature_1_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_2_title,
               subtitleText = Res.string.feature_2_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_3_title,
               subtitleText = Res.string.feature_3_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_4_title,
               subtitleText = Res.string.feature_4_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_5_title,
               subtitleText = Res.string.feature_5_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_6_title,
               subtitleText = Res.string.feature_6_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_7_title,
               subtitleText = Res.string.feature_7_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
           OnboardingSwipeUniquePageData(
               imageId = Res.drawable.ic_cyclone,
               titleText = Res.string.feature_8_title,
               subtitleText = Res.string.feature_8_subtitle,
               bgColor = Color(0XFFFFDAB9)
           ),
    OnboardingSwipeUniquePageData(
        imageId = Res.drawable.ic_cyclone,
        titleText = Res.string.feature_9_title,
        subtitleText = Res.string.feature_9_subtitle,
        bgColor = Color(0XFFFFDAB9)
    )
       )

    
    val pagerState = rememberPagerState { allSwipeDatas.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(allSwipeDatas[pagerState.currentPage].bgColor)
    ) {
        androidx.compose.foundation.pager.HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f),
        ) { page ->
            Box(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = 0.5f

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            ) {
                OnboardingSwipeUniquePage(allSwipeDatas[page])
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            activeColor = AppTheme.colors.activeIndicator,
            inactiveColor = AppTheme.colors.inactiveIndicator,
        )

        RoundedButton(
            text = stringResource(Res.string.continuee),
            onClick = {
                if (pagerState.currentPage + 1 < pagerState.pageCount) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    requestContinue()
                }
            },
            backgroundColor = AppTheme.colors.buttonBackground,
            contentColor = AppTheme.colors.textOnButton,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp,bottom = 48.dp)
        )
        Spacer(Modifier.height(8.dp))
    }
}

data class OnboardingSwipeUniquePageData(
    val imageId: DrawableResource,
    val titleText: StringResource,
    val subtitleText: StringResource,
    val bgColor: Color
)

@Composable
internal fun OnboardingSwipeUniquePage(data: OnboardingSwipeUniquePageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(data.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(280.dp)
        )

        Spacer(Modifier.height(16.dp))
        Heading(
            text = stringResource( data.titleText),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            color = Color(0XFF161415)
        )

        Spacer(Modifier.height(16.dp))

        SimpleLabel(
            text = stringResource( data.subtitleText),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center,
            color = Color(0XFF161415)
        )

        Spacer(Modifier.weight(1f))
    }
}
 

@Composable
 fun HorizontalPagerIndicator(
    pagerState: androidx.compose.foundation.pager.PagerState,
    modifier: Modifier = Modifier,
    pageCount: Int = pagerState.pageCount,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color,
    inactiveColor: Color ,
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {
    val stateBridge = remember(pagerState) {
        object : PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    HorizontalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}

@Composable
  fun HorizontalPagerIndicator(
    pagerState: PagerStateBridge,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color,
    inactiveColor: Color,
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {

    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)

            repeat(pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffset
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(
                            0f,
                            (pageCount - 1)
                                .coerceAtLeast(0)
                                .toFloat()
                        )

                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .then(
                    if (pageCount > 0) Modifier.background(
                        color = activeColor,
                        shape = indicatorShape,
                    )
                    else Modifier
                )
        )
    }
}

 interface PagerStateBridge {
    val currentPage: Int
    val currentPageOffset: Float
}

