package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
fun ListOfObjects(objects: List<IListSerializable>){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        items(objects.size){index ->
            Card (
                onClick = objects[index].getInteraction(),
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
                            text = objects[index].getMainField(),
                            fontSize = 20.sp,
                            color = colorResource(R.color.primaryText)
                        )
                        Text(
                            text = objects[index].getSubField(),
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
    }
}

@Preview
@Composable
fun PreviewListOfObjects(){
    ListOfObjects(
        objects = listOf(
            object : IListSerializable {
                override fun getMainField(): String {
                    return "Main field 1"
                }

                override fun getSubField(): String {
                    return "Sub field 1"
                }

                override fun getInteraction(): () -> Unit {
                    return {}
                }
            },
            object : IListSerializable {
                override fun getMainField(): String {
                    return "Main field 2"
                }

                override fun getSubField(): String {
                    return "Sub field 2"
                }

                override fun getInteraction(): () -> Unit {
                    return {}
                }
            },
            object : IListSerializable {
                override fun getMainField(): String {
                    return "Main field 3"
                }

                override fun getSubField(): String {
                    return "Sub field 3"
                }

                override fun getInteraction(): () -> Unit {
                    return {}
                }
            }
        )
    )
}
