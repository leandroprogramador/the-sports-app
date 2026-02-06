package br.leandro.thesportsapp.di

import br.leandro.thesportsapp.feature.countries.CountriesViewModel
import br.leandro.thesportsapp.feature.sportdetails.SportDetailsViewModel
import br.leandro.thesportsapp.feature.sportslist.SportsListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.module

val appModule = module {
    viewModel { SportsListViewModel(get()) }
    viewModel { SportDetailsViewModel() }
    viewModel { CountriesViewModel(get()) }
}