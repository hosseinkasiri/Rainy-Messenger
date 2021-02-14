package com.example.rainymessanger.model

class FriendlyMessage() {

    var id: String? = null
    var sender: String? = null
    var receiver: String? = null
    var text: String? = null

    constructor(
            id: String,
            sender: String,
            receiver: String,
            text: String
    ): this(){
        this.id = id
        this.sender = sender
        this.receiver = receiver
        this.text = text
    }
}