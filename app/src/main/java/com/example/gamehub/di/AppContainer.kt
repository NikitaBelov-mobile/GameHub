package com.example.gamehub.di

import android.content.Context
import androidx.room.Room
import com.example.gamehub.data.local.AppDatabase
import com.example.gamehub.data.remote.RawgApi
import com.example.gamehub.data.repository.GameRepositoryImpl
import com.example.gamehub.domain.interactor.GameInteractor
import com.example.gamehub.domain.interactor.GameInteractorImpl
import com.example.gamehub.domain.repository.GameRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainer(context: Context) {
    private val baseUrl = "https://api.rawg.io/api/"

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "gamehub.db"
    ).build()

    private val api: RawgApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RawgApi::class.java)

    private val repository: GameRepository = GameRepositoryImpl(api, db)

    val interactor: GameInteractor = GameInteractorImpl(repository)
    val database: AppDatabase get() = db
}
