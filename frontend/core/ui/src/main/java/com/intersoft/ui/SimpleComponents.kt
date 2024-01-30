package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PrimaryButton(buttonText: String, modifier: Modifier = Modifier, isEnabled: Boolean = true, action: () -> Unit ){
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
            },
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
    Column(modifier = Modifier
        .padding(paddingAmount.dp)
        .width(50.dp)){
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
fun DisabledTextField(textvalue:String, isMultiline: Boolean, visualTransformation: VisualTransformation = VisualTransformation.None){
    BasicTextField(textvalue, {},
        singleLine = isMultiline,
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
fun ParticipantNumberDisplayField(label: String, currentNumber: Int, maxNumber: Int){
    LabelText(text = label)
    BasicTextField("$currentNumber / $maxNumber", {},
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(fontSize = 25.sp),
        modifier = Modifier
            .background(color = colorResource(androidx.appcompat.R.color.dim_foreground_disabled_material_dark))
            .width(110.dp),
        decorationBox = {innerTextField ->
            Row(modifier = Modifier.width(30.dp)){}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectCard(data : IIterableObject, interaction: () -> Unit){
    Card (
        onClick = interaction,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.primary))
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .padding(10.dp)

            ) {
                Text(
                    text = data.getMainText(),
                    fontSize = 20.sp,
                    color = colorResource(R.color.primaryText)
                )
                Text(
                    text = data.getSecondaryText(),
                    fontSize = 15.sp,
                    color = colorResource(R.color.primaryText)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = colorResource(R.color.primaryText),
                    contentDescription = "Click to view details",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.End)

                )
            }
        }

    }
}

@Composable
fun UserListItem(username: String){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ){
        UserCard(username = username) {

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(username: String, interaction: () -> Unit) {
    Card(
        onClick = interaction,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.primary)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Text(
                text = username,
                fontSize = 30.sp,
                color = colorResource(R.color.primaryText)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = colorResource(R.color.primaryText),
                    contentDescription = "Click to view details",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.End)

                )
            }
        }

    }
}


@Composable
fun TextSearchField(visualTransformation: VisualTransformation = VisualTransformation.None,placeholder: String = "", onTextChanged: (String) -> Unit, onIconClicked: () -> Unit){

    var textValue by remember{
        mutableStateOf(placeholder)
    }
    Row {
        BasicTextField(
            textValue,
            {
                textValue = it
                onTextChanged(it)
            },
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(fontSize = 25.sp),
            modifier = Modifier.background(color = colorResource(R.color.inputField)),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    innerTextField()
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = colorResource(R.color.foregroundText),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(30.dp)
                            .clickable { onIconClicked()}
                    )
                }

            }
        )

    }

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

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Gray)
    }
}


@Composable
fun ConfirmationDialog(
    title: String,
    dialogText: String,
    onConfirmButton: () -> Unit,
    onDismissButton: () -> Unit,
){
    AlertDialog(onDismissRequest = onDismissButton,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            Button(onClick = onConfirmButton) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissButton) {
                Text(text = "Cancel")
            }
        }
    )
}


