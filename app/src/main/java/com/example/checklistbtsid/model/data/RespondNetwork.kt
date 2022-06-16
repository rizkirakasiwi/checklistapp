package com.example.checklistbtsid.model.data

data class RespondNetwork<T>(
    val `data`: T?,
    val errorMessage: String?,
    val message: String?,
    val statusCode: Int
)

data class ResponsesNetwork<T>(
    val `data`: List<T>,
    val errorMessage: String?,
    val message: String?,
    val statusCode: Int
)

data class EmptyResponse(
    val `data`: String?,
    val errorMessage: String?,
    val message: String?,
    val statusCode: Int
)