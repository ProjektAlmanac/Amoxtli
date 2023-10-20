package io.github.projektalmanac.amoxtli.backend.service

import io.github.projektalmanac.amoxtli.backend.entity.Book
import io.github.projektalmanac.amoxtli.backend.entity.Exchange
import io.github.projektalmanac.amoxtli.backend.entity.User
import io.github.projektalmanac.amoxtli.backend.enums.Status
import io.github.projektalmanac.amoxtli.backend.exception.*
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException
import io.github.projektalmanac.amoxtli.backend.generated.model.AceptarIntercambioRequestDto
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoIntercambioDto
import io.github.projektalmanac.amoxtli.backend.generated.model.GetIntercambios200ResponseDto
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository
import java.util.UUID

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
        idUsuario: Int,
        idIntercambio: Int,
        aceptarIntercambioRequestDto: AceptarIntercambioRequestDto
    ): IntercambioDto {

        val usuario = userRepository.findById(idUsuario).orElseThrow { UserNotFoundException(idUsuario) }

        var intercambio = exchangeRepository.findByIdAndUserAccepting(idIntercambio, usuario).orElseThrow { IntercambioNotFoundException(idIntercambio)}

        usuario.removeExchangesOfferor(intercambio)

        val idLibro = aceptarIntercambioRequestDto.idLibro?.toInt() ?: throw InvalidIdException()
        intercambio.bookOfferor = bookRepository.findById(idLibro) ?: throw ResourceNotFoundException("El libro no existe")
        intercambio.status = Status.ACEPTADO

        intercambio = exchangeRepository.save(intercambio)
        usuario.addExchangesAccepting(intercambio)
        userRepository.save(usuario)

       return ExchangeMapper.INSTANCE.toIntercambioDto(intercambio)
    }

    fun getIntercambio(userId: Int, exchangeId: Int): IntercambioDto {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val exchange = user.exchangesAccepting
            .asSequence()
            .plus(user.exchangesOfferor)
            .filter { it.id == exchangeId }
            .firstOrNull() ?: throw IntercambioNotFoundException(exchangeId)
        return ExchangeMapper.INSTANCE.toIntercambioDto(exchange)
    }

    fun getCode(userId: Int, exchangeId: Int): CodigoIntercambioDto {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val exchange = user.exchangesAccepting
            .asSequence()
            .plus(user.exchangesOfferor)
            .filter { it.id == exchangeId }
            .firstOrNull() ?: throw IntercambioNotFoundException(exchangeId)

        val code = UUID.randomUUID()

        if (exchange.userOfferor.id == userId) {
            exchange.offerorConfirmationCode = code.toString()
        } else {
            exchange.confirmationCodeAccepting = code.toString()
        }

        exchangeRepository.save(exchange)

        val dto = CodigoIntercambioDto()
        dto.codigo = code
        return dto
    }

    fun finalizeExchange(userId: Int, exchangeInt: Int, givenCode: UUID): IntercambioDto {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val exchange = user.exchangesAccepting
            .asSequence()
            .plus(user.exchangesOfferor)
            .filter { it.id == exchangeInt }
            .firstOrNull() ?: throw IntercambioNotFoundException(exchangeInt)

        val actualCode: String
        val otherUser: User
        val book: Book
        val otherBook: Book
        // El usuario que intenta finalizar el intercambio debe proporcionar el código del otro usuario
        if (exchange.userOfferor.id == userId) {
            actualCode = exchange.confirmationCodeAccepting
            otherUser = exchange.userAccepting
            book = exchange.bookOfferor
            otherBook = exchange.bookAccepting
        } else {
            actualCode = exchange.offerorConfirmationCode
            otherUser = exchange.userOfferor
            book = exchange.bookAccepting
            otherBook = exchange.bookOfferor
        }

        if (actualCode != givenCode.toString()) {
            throw InvalidExchangeCodeException()
        }

        val exchangeDto = ExchangeMapper.INSTANCE.toIntercambioDto(exchange)

        user.books.removeIf { it.id == book.id }
        otherUser.books.removeIf { it.id == otherBook.id }
        userRepository.save(user)
        userRepository.save(otherUser)
        exchangeRepository.delete(exchange)
        bookRepository.deleteById(exchange.bookOfferor.id)
        bookRepository.deleteById(exchange.bookAccepting.id)

        return exchangeDto
    }
}