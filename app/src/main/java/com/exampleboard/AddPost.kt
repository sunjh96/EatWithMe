package com.exampleboard

class AddPost {
    private var title: String
    private var address : String
    private var contents : String

    constructor(title: String, address: String, contents: String) {
        this.title = title
        this.address = address
        this.contents = contents
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
}