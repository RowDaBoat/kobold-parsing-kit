package kobold.parser

import kobold.lexer.lexer
import kobold.matchers.Accepted
import kobold.matchers.Symbol
import kobold.matchers.Token
import kobold.parser.dsl.terminal
import kobold.tokens.Number
import kobold.tokens.PlusOperator
import kobold.tokens.Sum
import kobold.tokens.Variable
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class `Parser should` {
    @Test
    fun `parse simple exprs`() {
        val lexer = lexer {
            ('a' to 'z') or ('A' to 'Z') with { Variable(it) }
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

        val tokens = lexer.tokenize("a + 10 + a + b")
        val result = parser.parse(tokens)
        assert(result is Accepted)
        assertEquals((result as Accepted).tree, expectedTree())
        println(result.tree?.show())
    }

    private fun expectedTree() =
        Sum(
            Variable("a"),
            PlusOperator(),
            Sum(
                Number("10"),
                PlusOperator(),
                Sum(
                    Variable("a"),
                    PlusOperator(),
                    Variable("b")
                )
            )
        )
}

fun Symbol.show(depth: Int = 0): String =
    indent(depth) + javaClass.name + showNode(depth)

private fun indent(depth: Int) = "  ".repeat(depth)

private fun Symbol.showNode(depth: Int) = when (this) {
    is Token -> "(${this.text})\n"
    else -> "\n${showChildren(depth)}"
}

private fun Symbol.showChildren(depth: Int) = children.joinToString("") { it.show(depth + 1) }
