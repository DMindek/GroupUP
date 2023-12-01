package com.intersoft.ui

interface IListSerializable {
    fun getMainField(): String
    fun getSubField(): String
    fun getInteraction(): () -> Unit
}