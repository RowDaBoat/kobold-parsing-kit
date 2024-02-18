package io.vexel.kobold.test.tokens

import io.vexel.kobold.Token

class PlusOperator : Token("+") {
    override fun equals(other: Any?) = other is PlusOperator
}