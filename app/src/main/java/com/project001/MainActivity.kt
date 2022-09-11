package com.project001

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.*
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.project001.ui.theme.Project001Theme
import java.net.URL
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project001Theme {
                // A surface container using the 'background' color from the theme
//                Login(getVideoUri())
                Column(modifier = Modifier.fillMaxSize()) {
                    Play(getVideoUri(),0.3f)
//                    Play(getVideoUri2(),0.5f)
//                    Play(getVideoUri3(),1f)

                }
            }
        }
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("good", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
    private fun getVideoUri2(): Uri {
        val rawId = resources.getIdentifier("tunnel", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
    private fun getVideoUri3(): Uri {
        val rawId = resources.getIdentifier("home", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
}
private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        useController = true
        resizeMode = RESIZE_MODE_ZOOM
    }


@Composable
fun Play(videoUri: Uri,forHeight :Float) {
    val context = LocalContext.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
 Column(         modifier = Modifier.fillMaxSize()
 ) {
     Box (
     ){  DisposableEffect(
         AndroidView(
             factory = { it.buildPlayerView(exoPlayer) },
             modifier = //Modifier.fillMaxSize()
             Modifier
                 .fillMaxWidth()
                 .fillMaxHeight(forHeight)
         )
     ) {
         onDispose {
             exoPlayer.release()
         }

     }}
     Spacer(modifier = Modifier.height(10.dp))
     Row(
         horizontalArrangement = Arrangement.SpaceEvenly,
         modifier = Modifier.fillMaxWidth()
     ) {
         Button(onClick = { exoPlayer.pause() }) {
             Text(text = "Pause")
         }
         Button(onClick = { exoPlayer.play()}) {
             Text(text = "Resume")
         }
         Button(onClick = { exoPlayer.stop()}) {
             Text(text = "Stop")
         }
     }

 }





}






