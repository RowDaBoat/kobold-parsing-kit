package kobold.tokens

import kobold.matchers.Token

class Variable(val name: String) : Token(name) {
    override fun equals(other: Any?) =
        other is Variable && other.name == name

    override fun hashCode() = 31 * super.hashCode() + name.hashCode()
}