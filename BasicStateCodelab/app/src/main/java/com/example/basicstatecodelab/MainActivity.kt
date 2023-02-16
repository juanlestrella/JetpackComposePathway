package com.example.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen(modifier = Modifier, WellnessViewModel())
                }
            }
        }
    }
}
@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

/**
 * The role of the StatelessCounter is to display the count
 * and call a function when you increment the count
 */
@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier){
    if (count > 0){
        Text("You've had $count glasses.")
    }
    Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10){
        Text("Add one")
    }
}

/**
 * StatefulCounter owns the state.
 * That means that it holds the count state and
 * modifies it when calling the StatelessCounter function.
 */
@Composable
fun StatefulCounter(modifier: Modifier){
    var waterCount by rememberSaveable {
        mutableStateOf(0)
    }
//    var juiceCount by rememberSaveable {
//        mutableStateOf(0)
//    }
    StatelessCounter(count = waterCount, onIncrement = { waterCount++ }, modifier = modifier)
    //StatelessCounter(count = juiceCount, onIncrement = { juiceCount++ }, modifier = modifier)
}

/**
@Composable
fun WaterCounter(modifier: Modifier = Modifier){
    val count = 0
    Column(modifier = modifier.padding(16.dp)){
        var count by remember {
            mutableStateOf(0)
        }
        if (count > 0){
            var showTask by remember { mutableStateOf(true) }
            if(showTask) {
                WellnessTaskItem(taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false}, modifier = Modifier)
            }
            Text(text = "You've had $count glasses.")
        }
        Row(modifier = Modifier.padding(top = 8.dp)){
            Button(onClick = {count++}, enabled = count < 10) {
                Text(text = "Add one")
            }
            Button(onClick = {count = 0}, Modifier.padding(start = 8.dp)) {
                Text(text = "Clear water count")
            }
        }
    }
}
**/
@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

/**
 * Stateful
 */
//@Composable
//fun WellnessTaskItem(
//    taskName: String,
//    onClose: () -> Unit,
//    modifier: Modifier = Modifier,
//){
//    var checkedState by rememberSaveable { mutableStateOf(false) }
//
//    WellnessTaskItem(
//        taskName = taskName,
//        checked = checkedState,
//        onCheckedChange = { newValue -> checkedState = newValue},
//        onClose = onClose,
//        modifier = modifier
//    )
//}

@Composable
fun WellnessScreen(
    modifier: Modifier,
    wellnessViewModel: WellnessViewModel) {
    //WaterCounter(modifier)
    //StatefulCounter(modifier = modifier)
    Column(modifier = modifier){
        StatefulCounter(modifier)

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = {task, checked -> wellnessViewModel.changeTaskChecked(task,checked)},
            onCloseTask = { task -> wellnessViewModel.remove(task)})
    }
}

@Preview
@Composable
fun PreviewWellnessScreen(){
    WaterCounter()
}