package com.fantasy.goliath.model


import java.io.Serializable

data class MatchItem(
    val competiton_id: String,
    val created_at: String,
    val datetime: String,
    val formate: String,
    val id: String,
    var match: String,
    var match_id: String,
    var short_title: String,
    val match_start_date: String,
    val match_start_time: String,
    var note: String,
    var status: String,

    var teama: TeamItem,
    var teamb: TeamItem,
    var innings: ArrayList<InningItem>,
    var question: ArrayList<QuestionAnsItem>,
    val updated_at: String
) : SuperCastClass(), Serializable


/*Parcelable {

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),

    )

    constructor() : this("", "","","","","","","","","","","","","","", "")

    // Write object values to parcel for storage
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(competiton_id)
        parcel.writeString(created_at)
        parcel.writeString(formate)
        parcel.writeString(id)
        parcel.writeString(match)
        parcel.writeString(match_id)
        parcel.writeString(match_start_date)
        parcel.writeString(match_start_time)
        parcel.writeString(status)
        parcel.writeString(teama_img)
        parcel.writeString(teama_short_name)
        parcel.writeString(teama_name)

        parcel.writeString(teamb_img)
        parcel.writeString(teamb_short_name)
        parcel.writeString(teamb_name)

        parcel.writeString(updated_at)


    }

    // Creator - used when un-parceling our parcel (creating the object)
    override fun describeContents(): Int {
        return 0
    }

    // De-serialize object
    companion object CREATOR : Parcelable.Creator<MatchItem> {
        override fun createFromParcel(parcel: Parcel): MatchItem {
            return MatchItem(parcel)
        }

        override fun newArray(size: Int): Array<MatchItem?> {
            return arrayOfNulls(size)
        }
    }
}*/