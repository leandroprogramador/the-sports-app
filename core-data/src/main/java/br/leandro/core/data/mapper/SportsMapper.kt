package br.leandro.core.data.mapper

import br.leandro.core.domain.model.Sport
import br.leandro.core.network.model.dto.SportsDto

fun SportsDto.toDomain() : Sport =
    Sport(
        id = this.idSport,
        name = this.strSport,
        image = this.strSportThumb,
        icon = this.strSportIconGreen
    )