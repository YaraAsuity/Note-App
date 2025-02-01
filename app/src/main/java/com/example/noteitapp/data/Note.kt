package com.example.noteitapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteitapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val subtitle: String, // Add this property
    val content: String,
    val color: Int = R.color.red,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
