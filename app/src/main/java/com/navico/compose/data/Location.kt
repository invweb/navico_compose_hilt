package com.navico.compose.data

import android.os.Parcel
import android.os.Parcelable

data class Location (
    val lat: Double,
    val lng: Double
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeDouble(lat)
        dest?.writeDouble(lng)
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel.readDouble(), parcel.readDouble())
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}