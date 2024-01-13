package com.example.data.androidTest.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    indices = [Index(
        value = arrayOf("name"),
    )]
)
data class RecipeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("idRecipe")
    val idRecipe: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("imageUrl")
    val imageUrl: String?

)