package io.vexel.kobold.test.parser.dsl.support

import io.vexel.kobold.Symbol
import io.vexel.kobold.Token

data class Label(override val children: List<Symbol>): Symbol {
    constructor(text: String) : this(text.map { Token(it.toString()) })

    val text = children.map { it as Token }.joinToString("") { it.text }

    override fun equals(other: Any?) = other is Label && text == other.text

    override fun toString() = text
}