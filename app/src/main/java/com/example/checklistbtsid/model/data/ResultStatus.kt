package com.example.checklistbtsid.model.data

sealed class ResultStatus<T>{
    class SUCCESS<T>(val data:T?):ResultStatus<T>()
    class FAILED<T>(val message:String?):ResultStatus<T>()
    class EXCEPTION<T>(val exception:Throwable):ResultStatus<T>()
    class NOTHING<T>():ResultStatus<T>()
}
