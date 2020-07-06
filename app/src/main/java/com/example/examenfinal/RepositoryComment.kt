package com.example.examenfinal

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RepositoryComment {
    object RetrofitRepository {
        const val BASE_URL = "https://my-json-server.typicode.com/rzkbrian/public_db/"

        fun getService() : Service {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(Service::class.java)
        }
    }
}