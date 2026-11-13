package com.example.notesapp.domain.model.repository.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}