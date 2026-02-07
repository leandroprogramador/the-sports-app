package br.leandro.core.domain.usecase

import br.leandro.core.domain.repository.LeagueRepository

class GetLeaguesUseCase(private val repository: LeagueRepository)  {
    suspend operator fun invoke(country: String, sport: String) = repository.getLeagues(country, sport)

}