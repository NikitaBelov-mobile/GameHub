package com.example.gamehub.domain

import com.example.gamehub.data.local.GameEntity

fun GameEntity.toDomain(): Game = Game(id, name, backgroundImage, rating, released)
