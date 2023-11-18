package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralDatePicker(onDismiss: () -> Unit, onConfirm: (Long) -> Unit){
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    val selectedDate = datePickerState.selectedDateMillis!!

    DatePickerDialog(
        shape = RoundedCornerShape(6.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onConfirm(selectedDate)
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }

            ) {
                Text(text = "Cancel")
            }
        }
    ){
        DatePicker(
            state = datePickerState,
            dateValidator = { timestamp ->
                timestamp > Instant.now().toEpochMilli()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTimePicker(onCancel: () -> Unit, onConfirm: () -> Unit){
    val state = rememberTimePickerState(is24Hour = true)


    TimePickerDialog(onCancel = { onCancel()}, onConfirm = { onConfirm() }) {
        TimePicker(
            state = state,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}




@Composable
fun CounterElement(label: String, action: (String) -> Unit = {}){
    var count by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Text(text = label,
            fontSize = 16.sp,
            color = colorResource(R.color.foregroundText)
        )

        Spacer(modifier = Modifier.width(15.dp))


        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {

            //Text(text = "Number of participants : $count", modifier = Modifier.padding(end = 20.dp))

            BasicTextField("$count",{action(it)},
                singleLine = true,
                textStyle = TextStyle(fontSize = 25.sp),
                modifier = Modifier
                    .background(color = colorResource(R.color.inputField))
                    .width(45.dp),
                decorationBox = {innerTextField ->
                    Row(modifier = Modifier.width(10.dp)){}
                    innerTextField()
                },
                enabled = false
            )

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick ={if(count>0)count--} ,
                colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary),
                contentColor = colorResource(R.color.primaryText),
                disabledContainerColor = colorResource(R.color.primary),
                disabledContentColor = colorResource(R.color.primaryText)
                )
            ) {
                Text(text = "-")
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = { count++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary),
                    contentColor = colorResource(R.color.primaryText),
                    disabledContainerColor = colorResource(R.color.primary),
                    disabledContentColor = colorResource(R.color.primaryText)
                )
            ) {
                Text(text = "+")
            }
        }
    }
}


