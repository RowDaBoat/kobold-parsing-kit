package kobold.tokens

import kobold.matchers.Token

class Number(val value: String) : Token(value) {
    override fun equals(other: Any?) =
        other is Number && other.value == value

    override fun hashCode() = 31 * super.hashCode() + value.hashCode()
}