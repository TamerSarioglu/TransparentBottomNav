package com.tamersarioglu.transparentbottombar.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.tamersarioglu.transparentbottombar.data.models.BottomNavItem
import com.tamersarioglu.transparentbottombar.presentation.navigation.NavRoutes

@Composable
fun TransparentBottomBar(
    navController: NavController,
    hazeState: HazeState
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Rounded.Home, NavRoutes.HOME, Color(0xFFFA6FFF)),
        BottomNavItem("Profile", Icons.Rounded.Person, NavRoutes.PROFILE, Color(0xFFFFA574)),
        BottomNavItem("Search", Icons.Rounded.Home, NavRoutes.SEARCH, Color(0xFF64FFDA)),
        BottomNavItem("Settings", Icons.Rounded.Settings, NavRoutes.SETTINGS, Color(0xFFADFF64))
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 1

    Box(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 64.dp)
            .fillMaxWidth()
            .height(64.dp)
            .hazeChild(state = hazeState, shape = CircleShape)
            .border(
                width = Dp.Hairline,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = .8f),
                        Color.White.copy(alpha = .2f),
                    ),
                ),
                shape = RectangleShape
            )
    ) {
        BottomBarContent(
            items = items,
            selectedIndex = selectedIndex,
            onItemSelected = { item ->
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )

        GlowingBackground(
            items = items,
            selectedIndex = selectedIndex
        )
    }
}

@Composable
private fun BottomBarContent(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (BottomNavItem) -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        ),
        LocalContentColor provides Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            for ((index, item) in items.withIndex()) {
                val alpha by animateFloatAsState(
                    targetValue = if (selectedIndex == index) 1f else .35f,
                    label = "alpha"
                )
                val scale by animateFloatAsState(
                    targetValue = if (selectedIndex == index) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                    label = "scale"
                )
                Column(
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha)
                        .fillMaxHeight()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                onItemSelected(item)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(imageVector = item.icon, contentDescription = "tab ${item.title}")
                    Text(text = item.title)
                }
            }
        }
    }
}

@Composable
private fun GlowingBackground(
    items: List<BottomNavItem>,
    selectedIndex: Int
) {
    val animatedSelectedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        label = "animatedSelectedIndex",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        )
    )

    val animatedColor by animateColorAsState(
        targetValue = items[selectedIndex].color,
        label = "animatedColor",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
    ) {
        val tabWidth = size.width / items.size
        drawCircle(
            color = animatedColor.copy(alpha = .6f),
            radius = size.height / 2,
            center = Offset(
                (tabWidth * animatedSelectedIndex) + tabWidth / 2,
                size.height / 2
            )
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
    ) {
        val path = Path().apply {
            addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
        }
        val length = PathMeasure().apply { setPath(path, false) }.length

        val tabWidth = size.width / items.size
        drawPath(
            path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    animatedColor.copy(alpha = 0f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 0f),
                ),
                startX = tabWidth * animatedSelectedIndex,
                endX = tabWidth * (animatedSelectedIndex + 1),
            ),
            style = Stroke(
                width = 6f,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(length / 2, length)
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransparentBottomBarPreview() {
    val context = LocalContext.current
    TransparentBottomBar(navController = NavController(
        context = context
    ), hazeState = HazeState())
}