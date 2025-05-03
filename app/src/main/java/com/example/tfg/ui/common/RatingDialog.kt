package com.example.tfg.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tfg.R

@Composable
fun RatingDialog(
    color: Color,
    userScore: Int,
    toggleRatingMenu: () -> Unit,
    saveRating: () -> Unit,
    changeUserScore: (newScore: Int) -> Unit,
    changeDeleted: (state: Boolean) -> Unit
) {
    var score by remember { mutableIntStateOf(userScore) }

    Dialog({ toggleRatingMenu() }) {
        Card(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = color)
        ) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.Companion.padding(10.dp)) {
                Column(modifier = Modifier.Companion.fillMaxWidth(), horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                    Text("Puntuación:", fontSize = 24.sp)
                    Text(if (score == 0) "-" else score.toString(), fontSize = 28.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    for (i in 1..10) {
                        val selected = i <= score
                        val scale by animateFloatAsState(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = 200)
                        )

                        Crossfade(targetState = selected, label = "starToggle") { isSelected ->
                            Icon(
                                painter = painterResource(
                                    if (isSelected) R.drawable.full_star else R.drawable.empty_star
                                ),
                                contentDescription = null,
                                modifier = Modifier.Companion
                                    .size(30.dp)
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale
                                    )
                                    .clickable {
                                        score = i
                                        changeUserScore(i)
                                        changeDeleted(false)
                                    }
                            )
                        }
                    }

                }
                Row(modifier = Modifier.Companion.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton({
                        score = 0
                        changeUserScore(0)
                        changeDeleted(true)
                    }) {
                        Text("Borrar")
                    }
                    TextButton({ saveRating() }) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}