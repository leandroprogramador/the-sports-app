package br.leandro.thesportsapp

import android.app.Application
import br.leandro.core.network.di.coreNetworkModule
import br.leandro.core.data.di.coreDataModule
import br.leandro.core.domain.di.coreDomainModule
import br.leandro.thesportsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheSportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TheSportsApp)
            allowOverride(true)
            modules(
                coreNetworkModule,
                coreDataModule,
                coreDomainModule,
                appModule
            )
        }
    }
}
