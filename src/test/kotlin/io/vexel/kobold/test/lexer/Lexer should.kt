package io.vexel.kobold.test.lexer

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer

import io.vexel.kobold.test.tokens.Number
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class `Lexer should` {
    @Test
    fun `match simple token definitions` () {
        val lexer = lexer {
            val alpha = ('a' to 'z') or ('A' to 'Z')
            val numeric = '0' to '9'

            alpha then (alpha or numeric).zeroOrMore() with { Label(it) }
            numeric.oneOrMore() with { Number(it) }
            ignore(" " or "!" or "," or ",")

        }

        val tokens = lexer.tokenize("H3llo Kobold Lexer! 9999")
        val expected = listOf(
            Label("H3llo"),
            Label("Kobold"),
            Label("Lexer"), Number("9999")
        )
        assertEquals(expected, tokens)
    }

    @Test
    fun `properly process line and column numbers` () {
        val lexer = lexer {
            "@".oneOrMore() with { Ats(it) }
            ignore(" " or "\n")
        }

        val text = """
            @@@@    @
            @@    @ @
        """.trimIndent()

        val tokens = lexer.tokenize(text)
        val expected = listOf(
            Ats("@@@@", 1, 1),
            Ats("@", 1, 9),
            Ats("@@", 2, 1),
            Ats("@", 2, 7),
            Ats("@", 2, 9)
        )
        assertEquals(expected, tokens)
    }

    class Ats(character: String, lineNumber: Int = 1, columnNumber: Int = 1, line: String = "") : Token(character) {
        init {
            this.addMetadata(lineNumber, columnNumber, line)
        }

        override fun toString(): String {
            return "Ats(text=$text, lineNumber=$lineNumber, columnNumber=$columnNumber, line=$line)"
        }

        override fun equals(other: Any?) =
            super.equals(other) && other is Ats &&
                (lineNumber == other.lineNumber) &&
                columnNumber == other.columnNumber &&
                line == other.line

        override fun hashCode(): Int =
            super.hashCode() + Objects.hash(lineNumber, columnNumber, line)
    }
}
