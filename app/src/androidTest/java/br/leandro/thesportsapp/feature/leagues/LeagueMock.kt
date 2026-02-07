package br.leandro.thesportsapp.feature.leagues

import br.leandro.core.data.local.entity.LeagueEntity
import br.leandro.core.domain.model.League

object LeagueMock {
    fun leagueList() = listOf(
        League(
            idLeague = "1234",
            division = 1,
            formedYear = "1959",
            badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
            complete = true,
            country = "Brazil",
            currentSeason = "2026",
            descriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
            descriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
            image = "",
            gender = "Male",
            league = "Campeonato Brasileiro",
            leagueAlternate = "Brasileirão",
            sport = "Soccer",
            tvRights = "Globo, CazéTV",
            isCup = false
        ),
        League(
            idLeague = "1235",
            division = 2,
            formedYear = "1986",
            badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_B_logo.png",
            complete = false,
            country = "Brazil",
            currentSeason = "2026",
            descriptionEN = "The Campeonato Brasileiro Série B is the second tier of professional football in Brazil.",
            descriptionPT = "O Campeonato Brasileiro Série A é a segunda divisão do futebol profissional no Brasil.",
            image = "",
            gender = "Male",
            league = "Campeonato Brasileiro Serie B",
            leagueAlternate = "Série B",
            sport = "Soccer",
            tvRights = "Rede Tv, Band",
            isCup = false
        )

    )
}