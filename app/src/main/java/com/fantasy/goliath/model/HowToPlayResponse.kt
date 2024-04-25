package com.fantasy.goliath.model

import java.util.ArrayList

data class HowToPlayResponse(
    val message: String,
    val status: Boolean,
    val data: PlayData
) : SuperCastClass(){
    data class  PlayData(
       val how_to_paly:ArrayList<HowToPlayItem>  )

}