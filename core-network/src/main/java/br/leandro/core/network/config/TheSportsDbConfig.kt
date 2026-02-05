package br.leandro.core.network.config

import br.leandro.core.network.BuildConfig

internal object TheSportsDbConfig {
   const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/${BuildConfig.API_KEY}/"
}