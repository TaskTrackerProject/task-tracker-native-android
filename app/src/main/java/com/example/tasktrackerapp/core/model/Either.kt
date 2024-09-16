package com.example.tasktrackerapp.core.model

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()

    fun isLeft(): Boolean = this is Left<L>
    fun isRight(): Boolean = this is Right<R>

    fun leftOrNull(): L? = if (this is Left<L>) this.value else null
    fun rightOrNull(): R? = if (this is Right<R>) this.value else null
}