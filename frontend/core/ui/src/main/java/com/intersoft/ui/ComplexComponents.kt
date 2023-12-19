package com.intersoft.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.intersoft.utils.DateTimeManager
import java.time.Instant



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralDatePicker(label: String, action: (Long) -> Unit){
    val showDatePicker = remember {
        mutableStateOf(false)
    }
    var selectedDateText by remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
    ) {
        LabelText(text = label,)
        if (showDatePicker.value) {
            SetupDatePickerDialogue(
                onDismiss = {
                    showDatePicker.value = false
                },
                onConfirm = {selectedDateInMillis ->
                    showDatePicker.value = false
                    selectedDateText = DateTimeManager.formatMillisDateToString(selectedDateInMillis)
                    action(selectedDateInMillis)
                }
            )
        }

        DisabledTextField(selectedDateText)
        PrimaryButton(buttonText = "Choose date") { showDatePicker.value = true }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetupDatePickerDialogue(onDismiss: () -> Unit, onConfirm: (Long) -> Unit){
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli() + 86400000 )
    val selectedDateInMillis = datePickerState.selectedDateMillis!!

    DatePickerDialog(
        shape = RoundedCornerShape(6.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onConfirm(selectedDateInMillis)
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

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DurationSelectionElement(onDurationPastMidnight: (String) -> Unit,onDurationInput: (Long) -> Unit ,onStartTimeInput: (Long) -> Unit){

    val showStartTimePicker = remember {
        mutableStateOf(false)
    }

    var selectedStartTimeText by remember {
        mutableStateOf("")
    }
    var durationHours by remember {
        mutableStateOf(-1)
    }
    var durationMinutes by remember {
        mutableStateOf(-1)
    }
    var selectedEndTimeText by remember {
        mutableStateOf("")
    }

    val selectedStartTimeList = mutableListOf<Int>()

    var selectedStartTime by remember{
        mutableStateOf(selectedStartTimeList)
    }

    var startTimeInMillis : Long

    var durationInMillis : Long

    val warningMessage = "Note: Set duration passes midnight and the end date is different from the start date"

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)

    ) {
        LabelText(text = "Duration")
        Column(verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            // Hours row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.width(80.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    LabelText(text = "Hours")
                }
                Column(modifier = Modifier.width(80.dp)) {
                    NumericTextInputField("", paddingAmount = 0){
                        if(it.isNotBlank()){
                            durationHours = it.toInt()
                            if(DateTimeManager.startTimeIsSet(selectedStartTimeText) && durationMinutes != -1){
                                selectedEndTimeText = DateTimeManager.calculateEndTime(durationHours,durationMinutes,selectedStartTime)
                                startTimeInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(selectedStartTime[0],selectedStartTime[1])
                                durationInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(durationHours,durationMinutes)

                                if(DateTimeManager.datePassesMidnight(selectedStartTime[0],durationHours))
                                    onDurationPastMidnight(warningMessage)
                                else
                                    onDurationPastMidnight("")

                                onStartTimeInput(startTimeInMillis)
                                onDurationInput(durationInMillis)
                            }
                        } else{
                            durationHours = -1
                        }
                    }
                }
            }
            // Minutes row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.width(80.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    LabelText(text = "Minutes")
                }
                Column(modifier = Modifier.width(80.dp)) {
                    NumericTextInputField("", paddingAmount = 0){
                        if(it.isNotBlank()){
                            durationMinutes = it.toInt()
                            if(DateTimeManager.startTimeIsSet(selectedStartTimeText) && durationHours != -1){
                                selectedEndTimeText = DateTimeManager.calculateEndTime(durationHours,durationMinutes,selectedStartTime)
                                startTimeInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(selectedStartTime[0],selectedStartTime[1])
                                durationInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(durationHours,durationMinutes)

                                if(DateTimeManager.datePassesMidnight(selectedStartTime[0],durationHours))
                                    onDurationPastMidnight(warningMessage)
                                else
                                    onDurationPastMidnight("")

                                onStartTimeInput(startTimeInMillis)
                                onDurationInput(durationInMillis)

                            }
                        } else{
                            durationMinutes = -1
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))
            // Start time row
            Row {
                Column (modifier = Modifier.width(100.dp)){
                    LabelText(text = "Start time:")
                }
                Column(horizontalAlignment = Alignment.End) {
                    DisabledTextField(selectedStartTimeText)
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    PrimaryButton(buttonText = "Choose start time", Modifier.width(180.dp),DateTimeManager.durationIsSet(durationHours,durationMinutes)) {
                        showStartTimePicker.value = true
                    }

                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            //End time row
            Row {
                Column (modifier = Modifier.width(100.dp)){
                    LabelText(text = "End time:")
                }
                Column(horizontalAlignment = Alignment.End) {


                    DisabledTextField(selectedEndTimeText)
                }
            }
        }
        if(showStartTimePicker.value)
            GeneralTimePicker(
                onConfirm = { selectedTime ->
                    selectedStartTime = selectedTime
                    selectedStartTimeText = DateTimeManager.formatStartTime(selectedTime)

                    if(DateTimeManager.durationIsSet(durationHours,durationMinutes)){
                        selectedEndTimeText = DateTimeManager.calculateEndTime(durationHours,durationMinutes,selectedTime)
                        durationInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(durationHours,durationMinutes)
                        onDurationInput(durationInMillis)
                    }

                    if(DateTimeManager.datePassesMidnight(selectedStartTime[0],durationHours))
                        onDurationPastMidnight(warningMessage)
                    else
                        onDurationPastMidnight("")


                    startTimeInMillis = DateTimeManager.calculateMillisFromHoursAndMinutes(selectedTime[0],selectedTime[1])
                    onStartTimeInput(startTimeInMillis)

                    showStartTimePicker.value = false
                },
                onCancel =  {showStartTimePicker.value = false})

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTimePicker(onCancel: () -> Unit, onConfirm: ( MutableList<Int>) -> Unit){
    val timePickerState = rememberTimePickerState(initialHour = 0, initialMinute = 0,is24Hour = true)
    val selectedTime = mutableListOf<Int>(timePickerState.hour,timePickerState.minute)

    SetupTimePickerDialogue(
        onCancel = { onCancel()},
        onConfirm = {
            onConfirm(selectedTime)
        }
    ) {
        TimePicker(
            state = timePickerState,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun SetupTimePickerDialogue(
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
                        onClick = {
                            onConfirm()
                        }
                    ) { Text("OK") }
                }
            }
        }
    }
}




@Composable
fun CounterElement(label: String, onNumberOfParticipantsSet: (Int) -> Unit = {}){
    var count by remember {
        mutableIntStateOf(0)
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField("$count",{onNumberOfParticipantsSet(count)},
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
                onClick = {
                    if(count>0){
                        count--
                        onNumberOfParticipantsSet(count)
                    }
                },
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
                onClick = {
                    count++
                    onNumberOfParticipantsSet(count)
                },
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



