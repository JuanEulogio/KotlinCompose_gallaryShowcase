package com.cs407.kotlincompose_gallaryshowcase


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs407.kotlincompose_gallaryshowcase.ui.theme.KotlinCompose_gallaryShowcaseTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinCompose_gallaryShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    gallaryLayout()
                }
            }
        }
    }
}

@Composable
fun gallaryLayout(modifier: Modifier = Modifier) {
    //keep track of the slide num
    var artNumber by remember { mutableStateOf(0) }
    //get art title, artist, year, and drawing id via resources
    //syncro drawing with title via: making the element index be our artNumber var
    //TODO: NOTE, for some reason, the line bellow makes the integers we're getting be
    // Zero all the time. I dont think we are able to get drawing resource id by making a array
    // in string res file holding the path to the drawings.
    // I tried to look up solution and it told me to use 'getResource()...' but im not able to use that
    // because my code doesnt allow me. Maybe im running a old android version? or its depricated?
    // Either way our alternative solution is to hard code the drawing id for the drawing we will have.
        //val artIDs= integerArrayResource(id = R.array.artDrawingID)
    val gallaryDrawingID: IntArray= intArrayOf(
        R.drawable.bacchus_and_ariadne,
        R.drawable.virgin_of_guadalupe_with_the_four_apparitions,
        R.drawable.the_kiss,
        R.drawable.last_of_the_buffalo,
        R.drawable.the_voyage_of_life_youth
    )

    //max is max arr index of gallary list
    val gallaryMaxSize= gallaryDrawingID.size -1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        //TODO: didnt use center because when i had verticalScroll on, everything centered,
        // as expected but that means we didnt have the extra space given as before (for some reason)
        // AND because
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(20.dp).verticalScroll(rememberScrollState())
    ) {
        //TODO: Note how we put spacer
        Spacer(modifier = modifier.height(80.dp))

        artPicture(
            art = gallaryDrawingID[artNumber],
            artDesc = stringArrayResource(id = R.array.artName)[artNumber],
            //TODO: note we will use weight to distribute space selectively
            modifier = modifier.weight(0.8f)
        )


        artTitleArtist(
            title = stringArrayResource(id = R.array.artName)[artNumber],
            artist = stringArrayResource(id = R.array.artAuthor)[artNumber],
            year= stringArrayResource(id = R.array.artYear)[artNumber],
            modifier = modifier.weight(0.1f)
        )

        //TODO: NOTE how we pass our function we want here
        navitationButtons(
            onNextButtonClick = {
                if(artNumber==gallaryMaxSize){
                    artNumber=0
                }else{ artNumber++}
            },
            onPreviousButtonClick = {
                if(artNumber==0){
                    artNumber=gallaryMaxSize
                }else{ artNumber--}
            },
            //TODO: passing over fillMaxSize kept our buttons when we turned horizontally.
            // for some reason they didnt show up otherwise. If you mess with the modifiers,
            // i believe it has something to do with it.
            // if the parameter modifier is used in row(), it will remove buttons from our
            // horizontal view, unless our other buttons are also the same modifier
            //In short, just pass the attributes we want our child composable to have
            modifier= modifier.weight(0.1f).fillMaxSize()
        )
    }
}

@Composable
fun artPicture(modifier: Modifier = Modifier,
               @DrawableRes art: Int,
               artDesc: String) {
    Surface(
        shadowElevation = 12.dp
    ) {
        Image(
            painter = painterResource(id = art),
            contentDescription = artDesc,
            modifier = Modifier.padding(16.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun artTitleArtist(modifier: Modifier = Modifier,
                   title: String,
                   artist: String,
                   year: String){

    Surface(
        color = colorResource(id = R.color.lightGray),
        modifier = Modifier.padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 12.dp, top= 12.dp, end = 12.dp)
            )
            Row {
                Text(
                    text = artist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
                )
                Text(
                    text = year,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end= 12.dp, bottom = 12.dp)
                )
            }
        }

    }

}

@Composable
fun navitationButtons(
    onNextButtonClick: () -> Unit,
    onPreviousButtonClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxSize(),
        //TODO: NOTE space between will bring our buttons to end of bottomStart and bottom
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.Bottom),
            onClick = {
                //TODO: Note how we pass in a function
                onPreviousButtonClick()
            }) {
            Text("Previous")
        }

        Button(
            modifier = Modifier.align(alignment = Alignment.Bottom),
            onClick = {
                onNextButtonClick()
            }) {
            Text("Next")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinCompose_gallaryShowcaseTheme {
        gallaryLayout()
    }
}