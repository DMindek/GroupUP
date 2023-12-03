package com.intersoft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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
fun TextInputField(label: String, visualTransformation: VisualTransformation = VisualTransformation.None, action: (String) -> Unit = {}){
    var textValue by remember{
        mutableStateOf("")
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
fun NavBar(navController: NavController, navBarItems: List<NavBarItem>){
    var currentRoute by remember {
        mutableStateOf(navBarItems[0].route)
    }

    NavigationBar{
        for (item in navBarItems){
            NavigationBarItem(
                selected = currentRoute == item.route,
                icon = { item.icon },
                onClick = {
                    currentRoute = item.route
                    navController.navigate(item.route)
            })
        }
    }
}

data class NavBarItem(val route: String, val icon: ImageVector)
