package br.leandro.thesportsapp

import android.app.Application
import br.leandro.core.network.di.theSportsDbNetworkModule
import br.leandro.core.data.di.theSportsDbDataModule
import br.leandro.core.domain.di.theSportsDomainModule
import org.koin.core.context.startKoin

class TheSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                theSportsDbNetworkModule,
                theSportsDbDataModule,
                theSportsDomainModule,
                appModule


            )
        }
    }
}