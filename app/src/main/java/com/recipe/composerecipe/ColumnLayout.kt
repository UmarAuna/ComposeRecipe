package com.recipe.composerecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ColumnLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurfaceViewColumn()
            /*ComposeRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }*/
        }
    }
}

@Composable
fun SurfaceViewColumn() {
    val scrollState = rememberScrollState()

    // Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF2F2F2))
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(R.drawable.happy_meal_small),
            contentDescription = "Content description for visually impaired",
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Happy Meal",
                style = TextStyle(
                    fontSize = 26.sp
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "800 Calories",
                style = TextStyle(
                    fontSize = 17.sp
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "$5.99",
                style = TextStyle(
                    color = Color(0XFF85BB65),
                    fontSize = 17.sp
                )
            )
        }
    }
}

/*@Composable
fun Greeting(name:String) {
    Text(text = "Hello $name!")
}*/

@Preview(showBackground = true)
@Composable
fun DefaultPreviewColumn() {
    SurfaceViewColumn()
    /*ComposeRecipeTheme {
        Greeting("Android")
    }*/
}
