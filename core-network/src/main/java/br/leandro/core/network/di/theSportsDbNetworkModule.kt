package br.leandro.core.network.di

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.retrofit.RetrofitFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val theSportsDbNetworkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single <OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        RetrofitFactory.create(get())
    }

    single<TheSportsDbApi> {
        get<Retrofit>().create(TheSportsDbApi::class.java)

    }


}