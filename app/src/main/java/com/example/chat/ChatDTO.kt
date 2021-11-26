package com.example.chat

class ChatDTO {
    private lateinit var userName : String
    private lateinit var message : String

    constructor() {}
    constructor(userName : String, message : String) {
        this.userName = userName
        this.message = message
    }

    fun setUserName(userName : String) {
        this.userName = userName
    }

    fun setMessage(message : String) {
        this.message = message
    }

    fun getUserName() : String {
        return userName
    }

    fun getMessage() : String {
        return message
    }

}