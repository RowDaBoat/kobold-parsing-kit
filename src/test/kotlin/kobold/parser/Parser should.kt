package kobold.parser

import kobold.lexer.lexer
import kobold.Accepted
import kobold.Rejected
import kobold.TrailingTokens
import kobold.UnexpectedToken
import kobold.matchers.Symbol
import kobold.matchers.Token
import kobold.parser.dsl.terminal
import kobold.tokens.Number
import kobold.tokens.PlusOperator
import kobold.tokens.Sum
import kobold.tokens.Variable
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class `Parser should` {
    val lexer = lexer {
        (('a' to 'z') or ('A' to 'Z')).oneOrMore() with { Variable(it) }
        ('0' to '9').oneOrMore() with { Number(it) }
        "+" with { PlusOperator() }
        ignore(" ")
    }

    val parser = parser {
        val operator = terminal<PlusOperator>()
        val number = terminal<Number>()
        val variable = terminal<Variable>()

        val expression = nonTerminal()
        val sum = nonTerminal { Sum(it) }
        val value = nonTerminal()

        expression from anyOf(sum, value)
        sum from sequence(value, operator, expression)
        value from (number or variable)

        expression
    }

    @Test
    fun `parse a simple expressions`() {
        val tokens = lexer.tokenize("some + 10 + value + b")
        val result = parser.parse(tokens)

        assertIs<Accepted>(result)
        assertEquals((result).tree, expectedTree())
        println(result.tree?.show())
    }

    @Test
    fun `reject a matching expression with a non matching trail`() {
        val tokens = lexer.tokenize("some + 10 + value trail")
        val result = parser.parse(tokens)

        assertIs<Rejected>(result)
    }

    @Test
    fun `reject non matching expressions`() {
        val tokens = lexer.tokenize("some + 10 ++ value + b")
        val result = parser.parse(tokens)

        assertIs<Rejected>(result)
        assertEquals(1, result.lineNumber)
        assertEquals(11, result.columnNumber)
    }

    @Test
    fun `reject trailing tokens even if all tokens before are matching`() {
        val lexer = lexer {
            "a" with { Token("a") }
            "b" with { Token("b") }
        }

        val parser = parser { sequence("a", "a", "a") }
        val tokens = lexer.tokenize("aaabbb")

        val result = parser.parse(tokens)
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<TrailingTokens>(reason)

        val expectedTrail = listOf(Token("b"), Token("b"), Token("b"))
        assertEquals(expectedTrail, reason.trail.toList())
    }

    private fun expectedTree() =
        Sum(
            Variable("some"),
            PlusOperator(),
            Sum(
                Number("10"),
                PlusOperator(),
                Sum(
                    Variable("value"),
                    PlusOperator(),
                    Variable("b")
                )
            )
        )
}

fun Symbol.show(depth: Int = 0): String =
    indent(depth) + javaClass.simpleName + showNode(depth)

private fun indent(depth: Int) = "  ".repeat(depth)

private fun Symbol.showNode(depth: Int) = when (this) {
    is Token -> "(${this.text})\n"
    else -> "\n${showChildren(depth)}"
}

private fun Symbol.showChildren(depth: Int) = children.joinToString("") { it.show(depth + 1) }
