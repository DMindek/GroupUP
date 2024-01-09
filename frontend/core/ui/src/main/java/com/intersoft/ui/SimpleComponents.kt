package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
fun SecondaryButton(buttonText: String, modifier: Modifier = Modifier, action: () -> Unit){
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.secondary),
            contentColor = colorResource(R.color.secondaryText),
            disabledContainerColor = colorResource(R.color.secondary),
            disabledContentColor = colorResource(R.color.secondaryText)
        )
    ){
        Text(text = buttonText)
    }
}
@Composable
fun PrimaryButton(buttonText: String, modifier: Modifier = Modifier, isEnabled: Boolean, action: () -> Unit ){
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primary),
            contentColor = colorResource(R.color.primaryText),
            disabledContainerColor = colorResource(R.color.primary),
            disabledContentColor = colorResource(R.color.primaryText)
        ),
        enabled = isEnabled
    ){
        Text(text = buttonText)
    }
}

@Composable
fun MultiLineTextInputField(label: String, visualTransformation: VisualTransformation = VisualTransformation.None, placeholder: String = "", action: (String) -> Unit = {}){
    var textValue by remember{
        mutableStateOf(placeholder)
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
            singleLine = false,
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
fun TextInputField(label: String, visualTransformation: VisualTransformation = VisualTransformation.None,placeholder: String = "", action: (String) -> Unit = {}){
    var textValue by remember{
        mutableStateOf(placeholder)
    }
    Column(){
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
fun NumericTextInputField(label: String, visualTransformation: VisualTransformation = VisualTransformation.None, placeholder: String = "",paddingAmount: Int, action: (String) -> Unit = {}){
    var textValue by remember{
        mutableStateOf(placeholder)
    }
    val pattern = remember {Regex("^[0-9]{0,2}\$")}
    Column(modifier = Modifier.padding(paddingAmount.dp).width(50.dp)){
        Text(text = label,
            fontSize = 16.sp,
            color = colorResource(R.color.foregroundText)
        )
        BasicTextField(textValue,
            {
                if(it.matches(pattern)){
                    textValue = it
                    action(it)
                }
            },
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(fontSize = 25.sp),
            modifier = Modifier.background(color = colorResource(R.color.inputField)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
        lineHeight = 50.sp,
        modifier = Modifier
            .fillMaxWidth()
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

@Composable
fun IconInformationText(icon: ImageVector, text: String){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge )
    }

}

@Composable
fun WarningText(text: String){
    Text(
        text = text,
        color = colorResource(R.color.foregroundText),
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

