package com.intersoft.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(buttonText: String, action: () -> Unit){
     Button(
         onClick = action,
         colors = ButtonDefaults.buttonColors(
             containerColor = Color(R.color.primary),
             contentColor = Color(R.color.primaryText),
             disabledContainerColor = Color(R.color.primary),
             disabledContentColor = Color(R.color.primaryText)
         )
    ){
         Text(text = buttonText)
     }
}

@Composable
fun TextInputField(label: String, action: (String) -> Unit){
    Column{
        Text(text = label,
            fontSize = 8.sp,
            color = Color(R.color.foregroundText)
        )
        BasicTextField("", action)
    }
}
