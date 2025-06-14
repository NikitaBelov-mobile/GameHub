package com.example.gamehub.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName
    )

    @get:Rule
    val rule = helper

    @Test
    fun migrate1To2() {
        val db = helper.createDatabase("test.db", 1)
        db.execSQL("CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, backgroundImage TEXT, rating REAL, released TEXT, lastUpdated INTEGER NOT NULL)")
        db.close()

        helper.runMigrationsAndValidate("test.db", 2, true, MIGRATION_1_2)
    }
}
