package io.github.projektalmanac.amoxtli.backend.service

import io.github.projektalmanac.amoxtli.backend.entity.Exchange
import io.github.projektalmanac.amoxtli.backend.enums.Status
import io.github.projektalmanac.amoxtli.backend.exception.EmptyResourceException
import io.github.projektalmanac.amoxtli.backend.exception.IntercambioNotFoundException
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException
import io.github.projektalmanac.amoxtli.backend.generated.model.AceptarIntercambioRequestDto
import io.github.projektalmanac.amoxtli.backend.generated.model.GetIntercambios200ResponseDto
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository

open class UserServiceKt (private val userRepository: UserRepository, private val exchangeRepository: ExchangeRepository, private val bookRepository: BookRepository) {


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

        val usuario = userRepository.findById(idUsuario).orElseThrow { UserNotFoundException(idUsuario) }

        val intercambio = exchangeRepository.findByIdAndUserAccepting(idIntercambio, usuario).orElseThrow { IntercambioNotFoundException(idIntercambio)}

        usuario.removeExchangesOfferor(intercambio)

        intercambio.bookOfferor = bookRepository.findById(aceptarIntercambioRequestDto?.idLibro?.toInt() ?: throw ResourceNotFoundException("El libro no existe"))
        intercambio.status = Status.ACEPTADO

        exchangeRepository.save(intercambio)
        usuario.addExchangesAccepting(intercambio)
        userRepository.save(usuario)

       return ExchangeMapper.INSTANCE.intercambioToIntercambioDto(intercambio)
    }
}