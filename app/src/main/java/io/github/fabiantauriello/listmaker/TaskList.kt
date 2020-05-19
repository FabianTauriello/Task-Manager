package io.github.fabiantauriello.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskList(val name: String, val tasks: ArrayList<String> = ArrayList()) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    companion object CREATOR: Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source) // calls the secondary constructor above

        // returns array of task lists
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        // convert task list into a parcel
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    // not working with file descriptors
    override fun describeContents() = 0

}