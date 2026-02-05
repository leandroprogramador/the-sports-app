package br.leandro.core.data.mapper

import br.leandro.core.domain.model.Sport
import br.leandro.core.network.model.dto.SportsDto

fun SportsDto.toDomain() : Sport =
    Sport(
        description = this.strSportDescription,
        icon = this.strSportIconGreen,
        id = this.idSport,
        image = this.strSportThumb,
        name = this.strSport,
    )