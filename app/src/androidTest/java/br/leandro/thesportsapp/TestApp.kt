package br.leandro.thesportsapp

import android.app.Application
import br.leandro.core.data.di.theSportsDbDataModule
import br.leandro.core.domain.di.theSportsDomainModule
import br.leandro.core.network.di.theSportsDbNetworkModule
import br.leandro.thesportsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}