package br.leandro.core.data.mapper

import br.leandro.core.data.local.entity.CountryEntity
import br.leandro.core.domain.model.Country
import br.leandro.core.network.model.dto.CountryDto

fun CountryDto.toDomain() = Country(name_en, flag_url_32)
fun CountryEntity.toDomain() = Country(name, flagUrl)
fun Country.toEntity() = CountryEntity(name, flag)
