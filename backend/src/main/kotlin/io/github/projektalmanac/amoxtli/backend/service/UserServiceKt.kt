package io.github.projektalmanac.amoxtli.backend.service

import io.github.projektalmanac.amoxtli.backend.exception.EmptyResourceException
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException
import io.github.projektalmanac.amoxtli.backend.generated.model.GetIntercambios200ResponseDto
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository

open class UserServiceKt {

    private val userRepository: UserRepository? = null
    fun getIntercambios(id: Int): GetIntercambios200ResponseDto {

        val userOpt = userRepository!!.findById(id)

        if (userOpt.isEmpty) {
            throw UserNotFoundException(id)
        }

        val user = userOpt.get()

        val intercambios = user.exchangesOfferor
        intercambios.addAll(user.exchangesAccepting)

        if (intercambios.isEmpty()) {
            throw EmptyResourceException()
        }

        return ExchangeMapper.INSTANCE.toGetIntercambios200ResponseDto(intercambios)
    }
}