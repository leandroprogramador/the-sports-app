package br.leandro.core.data.mapper

import br.leandro.core.data.local.entity.SportEntity
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

fun SportEntity.toDomain() = Sport(
    id = this.id,
    name = this.name,
    icon = this.iconUrl,
    image = this.imageUrl,
    description = this.description
)

fun Sport.toEntity() = SportEntity(
    id = this.id,
    name = this.name,
    iconUrl = this.icon,
    imageUrl = this.image,
    description = this.description

)