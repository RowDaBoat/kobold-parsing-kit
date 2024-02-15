package kobold.tokens

import kobold.matchers.Token

class PlusOperator : Token("+") {
    override fun equals(other: Any?) = other is PlusOperator
}