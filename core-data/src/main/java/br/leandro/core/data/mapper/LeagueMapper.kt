package br.leandro.core.data.mapper

import br.leandro.core.data.local.entity.LeagueEntity
import br.leandro.core.domain.model.League
import br.leandro.core.network.model.dto.LeagueDto

fun LeagueDto.toDomain() = League(
    idLeague = this.idLeague,
    division = if(this.intDivision.toInt() == 0) 1 else this.intDivision.toInt(),
    isCup = this.intDivision == "99",
    formedYear = this.intFormedYear,
    badge = this.strBadge,
    complete = this.strComplete.equals("yes", true),
    country = this.strCountry,
    currentSeason = this.strCurrentSeason,
    descriptionEN = this.strDescriptionEN,
    descriptionPT = this.strDescriptionPT ?: "",
    image = this.strFanart1 ?: "",
    gender = this.strGender,
    league = this.strLeague,
    leagueAlternate = this.strLeagueAlternate,
    sport = this.strSport,
    tvRights = this.strTvRights ?: ""
)

fun LeagueEntity.toDomain() = League (
    idLeague = this.idLeague,
    division = this.division.toInt(),
    isCup = this.division == "99",
    formedYear = this.formedYear,
    badge = this.badge,
    complete = this.complete.equals("yes", true),
    country = this.country,
    currentSeason = this.currentSeason,
    descriptionEN = this.descriptionEN,
    descriptionPT = this.descriptionPT,
    image = this.image,
    gender = this.gender,
    league = this.league,
    leagueAlternate = this.leagueAlternate,
    sport = this.sport,
    tvRights = this.tvRights
)

fun League.toEntity() = LeagueEntity(
    idLeague = this.idLeague,
    division = this.division.toString(),
    formedYear = this.formedYear,
    badge = this.badge,
    complete = if(this.complete) "yes" else "no",
    country = this.country,
    currentSeason = this.currentSeason,
    descriptionEN = this.descriptionEN,
    descriptionPT = this.descriptionPT ?: "",
    image = this.image,
    gender = this.gender,
    league = this.league,
    leagueAlternate = this.leagueAlternate,
    sport = this.sport,
    tvRights = this.tvRights
)






