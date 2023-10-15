package io.github.projektalmanac.amoxtli.backend.service

import io.github.projektalmanac.amoxtli.backend.entity.Exchange
import io.github.projektalmanac.amoxtli.backend.exception.*
import io.github.projektalmanac.amoxtli.backend.generated.model.AceptarIntercambioRequestDto
import io.github.projektalmanac.amoxtli.backend.generated.model.GetIntercambios200ResponseDto
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper
import io.github.projektalmanac.amoxtli.backend.repository.*

open class UserServiceKt (private val userRepository: UserRepository, private val exchangeRepository: ExchangeRepository) {


    fun getIntercambios(id: Int): GetIntercambios200ResponseDto {

        val user = userRepository.findById(id).orElseThrow { UserNotFoundException(id) }

        val intercambios = mutableListOf<Exchange>()
        intercambios.addAll(user.exchangesOfferor)
        intercambios.addAll(user.exchangesAccepting)

        if (intercambios.isEmpty()) {
            throw EmptyResourceException()
        }

        return ExchangeMapper.INSTANCE.toGetIntercambios200ResponseDto(intercambios)
    }

    fun aceptarIntercambio(
        idUsuario: Int?,
        idIntercambio: Int?,
        aceptarIntercambioRequestDto: AceptarIntercambioRequestDto?
    ): IntercambioDto? {

        val userOpt = userRepository.findById(idUsuario) ?: throw UserNotFoundException(idUsuario)
        val user = userOpt.get()

        val intercambioOpt = exchangeRepository.findByIdAndUserAccepting(idIntercambio, user) ?: throw IntercambioNotFoundException(idIntercambio)



        return null
    }
}