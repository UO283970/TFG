package com.example.tfg.model.user.userActivities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class UserActivityAdapter : JsonSerializer<Activity>,JsonDeserializer<Activity> {
    override fun serialize(src: Activity, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = context.serialize(src).asJsonObject

        jsonObject.addProperty("activityType",when(src){
            is ReviewActivity -> "review"
            is RatingActivity -> "rating"
        })

        return jsonObject
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Activity {
        val jsonObject = json.asJsonObject

        return when (jsonObject.get("activityType").asString){
            "review" -> context.deserialize(jsonObject, ReviewActivity::class.java)
            "rating" -> context.deserialize(jsonObject, RatingActivity::class.java)
            else -> throw IllegalArgumentException("This activity class is not recognize for deserializing")
        }
    }
}