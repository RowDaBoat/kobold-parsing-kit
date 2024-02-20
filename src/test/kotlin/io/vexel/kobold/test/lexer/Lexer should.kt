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
            Ats("@@@@", "@@@@    @", 1, 1, 0, 4),
            Ats("@", "@@@@    @", 1, 9, 8, 9),
            Ats("@@", "@@    @ @", 2, 1, 10, 12),
            Ats("@", "@@    @ @", 2, 7, 16, 17),
            Ats("@", "@@    @ @", 2, 9, 18, 19)
        )
        assertEquals(expected, tokens)
    }

    class Ats(
        text: String,
        lineText: String = "",
        line: Int = 1,
        column: Int = 1,
        start: Int = 0,
        end: Int = 0
    ) : Token(text) {
        init {
            this.addMetadata(lineText, line, column, start, end)
        }

        override fun toString(): String {
            return "Ats(text=$text, line=$line, column=$column, start=$start, end=$end)"
        }

        override fun equals(other: Any?) =
            super.equals(other) && other is Ats &&
                lineText == other.lineText &&
                line == other.line &&
                column == other.column &&
                start == other.start &&
                end == other.end

        override fun hashCode(): Int =
            super.hashCode() + Objects.hash(line, column)
    }
}
