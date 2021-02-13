package com.example.rainymessanger.model

class User() {
    var displayName: String? = null
    var status: String? = null
    var image: String? = null
    var thumbImage: String? = null

    constructor(
            displayName: String,
            status: String,
            image: String,
            thumbImage: String
    ): this(){
        this.displayName = displayName
        this.status = status
        this.image = image
        this.thumbImage = thumbImage
    }
}