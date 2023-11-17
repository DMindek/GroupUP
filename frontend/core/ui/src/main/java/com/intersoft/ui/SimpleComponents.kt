package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun PrimaryButton(buttonText: String, modifier: Modifier = Modifier, action: () -> Unit){
     Button(
         onClick = action,
         modifier = modifier,
         colors = ButtonDefaults.buttonColors(
             containerColor = colorResource(R.color.primary),
             contentColor = colorResource(R.color.primaryText),
             disabledContainerColor = colorResource(R.color.primary),
             disabledContentColor = colorResource(R.color.primaryText)
         )
    ){
         Text(text = buttonText)
     }
}

@Composable
fun TextInputField(label: String, visualTransformation: VisualTransformation = VisualTransformation.None, action: (String) -> Unit = {}){
    var textValue by remember{
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(20.dp)){
        Text(text = label,
            fontSize = 16.sp,
            color = colorResource(R.color.foregroundText)
        )
        BasicTextField(textValue,
            {
                textValue = it
                action(it)
            },
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(fontSize = 25.sp),
            modifier = Modifier.background(color = colorResource(R.color.inputField)),
            decorationBox = {innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()){}
                innerTextField()
            }
        )
    }
}

@Composable
fun DisabledTextField(textvalue:String, visualTransformation: VisualTransformation = VisualTransformation.None){
        BasicTextField(textvalue, {},
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(fontSize = 25.sp),
            modifier = Modifier.background(color = colorResource(androidx.appcompat.R.color.dim_foreground_disabled_material_dark)),
            decorationBox = {innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()){}
                innerTextField()
            },
            enabled = false
        )
}

@Composable
fun TitleText(text: String){
    Text(
        text = text,
        fontSize = 40.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    )
}

@Composable
fun LabelText(text: String){
    Text(
        text = text,
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        color = colorResource(R.color.foregroundText)
    )
}

@Composable
fun ErrorText(text: String){
    Text(
        text = text,
        color = colorResource(R.color.errorColor),
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}