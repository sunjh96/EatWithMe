package com.cookandroid.eatwithme

class ChatDTO {
    private lateinit var user_name : String
    private lateinit var message : String

    constructor(){}
    constructor(user_name : String, message : String) {
        this.user_name = user_name
        this.message = message
    }

    fun setUser_name(user_name : String) {
        this.user_name = user_name
    }

    fun setMessage(message : String) {
        this.message = message
    }

    fun getUser_name() : String {
        return user_name
    }

    fun getMessage() : String {
        return message
    }

}