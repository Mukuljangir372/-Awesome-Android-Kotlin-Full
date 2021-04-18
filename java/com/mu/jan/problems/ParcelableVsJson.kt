package com.mu.jan.problems

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

/**
 * Parcelable vs serializable vs Json string
 *
 * Serializable - convert object into byte stream. But it's a java specific interface.
 * Parcelable - convert object into byte stream. But it's a android specific interface.
 * Json string - convert object into json string using gson
 *
 * Performance -
 * Parcelable having better performance than json string and serializable.
 *
 * Parcelable -
 * 1. More boilerplate code
 * 2. Better performance
 *
 * Json string -
 * 1. Less code
 * 2. Less performance (only in larger objects)
 *
 *
 */
class Car(
    private val id: String? = null,
    private val name: String? = null,
    private val engine: String? = null
): Parcelable{

    /**
     * Note : For below parcelable code generation,
     * use Parcelable Code generator plugin
     */
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(engine)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }

}
class Activity1: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //onClick
        val car = Car("id","name","engine")
        val intent = Intent().apply {
            putExtra("car",car)
        }
    }
}
class Activity2: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get intent
        val car = intent.getParcelableExtra<Car>("car")

    }
}
























