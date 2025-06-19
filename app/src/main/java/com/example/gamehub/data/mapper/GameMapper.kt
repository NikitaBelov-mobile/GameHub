package com.example.gamehub.data.mapper

import com.example.gamehub.data.local.GameEntity
import com.example.gamehub.data.remote.GameDto
import com.example.gamehub.domain.model.Game

fun GameDto.toEntity(currentTime: Long = System.currentTimeMillis()): GameEntity =
    GameEntity(id, name, backgroundImage, rating, released, currentTime)

fun GameEntity.toDomain(): Game =
    Game(id, name, backgroundImage, rating, released)
