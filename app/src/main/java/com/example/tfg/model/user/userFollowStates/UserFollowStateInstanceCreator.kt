package com.example.tfg.model.user.userFollowStates

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class UserFollowStateInstanceCreator : JsonSerializer<UserFollowStateEnum>, JsonDeserializer<UserFollowStateEnum> {
    override fun serialize(src: UserFollowStateEnum, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        var jsonFollowState = context.serialize(src)

        return context.serialize(src)
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UserFollowStateEnum? {
        return context?.deserialize(json, typeOfT)
    }

}