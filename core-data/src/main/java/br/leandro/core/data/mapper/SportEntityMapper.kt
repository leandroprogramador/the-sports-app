package br.leandro.core.data.mapper

import br.leandro.core.data.local.entity.SportEntity
import br.leandro.core.domain.model.Sport

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