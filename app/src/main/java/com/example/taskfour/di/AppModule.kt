package com.example.taskfour.di

import android.content.Context
import androidx.room.Room
import com.example.taskfour.repository.TaskFourRepository
import com.example.taskfour.room.CryptoDao
import com.example.taskfour.room.CryptoDatabase
import com.example.taskfour.service.CryptoApi
import com.example.taskfour.service.NewsApi
import com.example.taskfour.utilies.Constants
import com.google.android.gms.tasks.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitCrypto(): CryptoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_COIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitNews(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_COIN_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        cryptoApi: CryptoApi,
        newsApi: NewsApi,
        cryptoDao: CryptoDao,
    ): TaskFourRepository {
        return TaskFourRepository(cryptoApi, newsApi, cryptoDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CryptoDatabase {
        return Room.databaseBuilder(
            appContext,
            CryptoDatabase::class.java,"coins",
        ).build()
    }

    @Provides
    fun provideCoinDao(database: CryptoDatabase): CryptoDao {
        return database.cryptoDao()
    }
}

