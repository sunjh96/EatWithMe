package com.cookandroid.eatwithme

import java.time.LocalDate

class AddPost {
    private var title: String
    private var address : String
    private var contents : String
    private var kakao_name : String
    private var kakao_id : String
    private var createdAt : String

    constructor(title: String, address: String, contents: String, kakao_name: String, kakao_id: String, createdAt : String) {
        this.title = title
        this.address = address
        this.contents = contents
        this.kakao_name = kakao_name
        this.kakao_id = kakao_id
        this.createdAt = createdAt
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(Address: String) {
        this.address = address
    }


    fun getContents(): String {
        return contents
    }

    fun setContents(contents: String) {
        this.contents = contents
    }

    fun getKakao_name(): String {
        return kakao_name
    }

    fun setKakao_name(kakao_name: String) {
        this.kakao_name = kakao_name
    }

    fun getKakao_id(): String {
        return kakao_id
    }

    fun setKakao_id(kakao_id: String) {
        this.kakao_id = kakao_id
    }

    fun getCreatedAt() : String {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }
}