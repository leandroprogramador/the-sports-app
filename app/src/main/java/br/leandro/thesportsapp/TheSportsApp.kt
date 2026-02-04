package br.leandro.thesportsapp

import android.app.Application
import br.leandro.core.network.di.theSportsDbNetworkModule
import org.koin.core.context.startKoin

class TheSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                theSportsDbNetworkModule
            )
        }
    }
}