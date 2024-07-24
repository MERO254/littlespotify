package com.example.littlespotify

data class music(var artist:String = "",
                 var audio:String = "",
                 var comment:MutableList<String> = mutableListOf(),
                 var song:String = "")
