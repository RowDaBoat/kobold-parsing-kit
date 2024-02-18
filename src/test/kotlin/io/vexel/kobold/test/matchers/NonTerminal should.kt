package io.vexel.kobold.test.matchers

import MatcherMemo
import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.Token
import io.vexel.kobold.UnexpectedToken
import io.vexel.kobold.matchers.*
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `NonTerminal should` {
    @Test
    fun `accept zero or more repetitions`() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(OrderedChoice(Concatenation(TerminalByContent(Token('a')), manyAs), Empty()))

        val result = manyAs.match(tokens("aaaaaaaaaaaaaaaaa"))

        assertIs<Accepted>(result)
    }

    @Test
    fun `accept zero or more repetitions ending with other character`() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(OrderedChoice(Concatenation(TerminalByContent(Token('a')), manyAs), Empty()))

        val string = "aaaaaaaaaaaaaaaab"
        val result = manyAs.match(tokens(string))
        assertIs<Accepted>(result)
        assertEquals(1, result.rest.count())
    }

    @Test
    fun `reject zero or more repetitions starting with other character`() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(Concatenation(TerminalByContent(Token('a')), manyAs))

        val result = manyAs.match(tokens("baaaaaaaaaaaaaaaa"))

        assertIs<Rejected>(result)
        assertIs<UnexpectedToken>(result.reason)
    }

    @Test
    fun `accept recursive grammars matched with a nested expression`() {
        val memo = MatcherMemo()
        val closures = NonTerminal(memo)
        val closure = Concatenation(TerminalByContent(Token('(')), Concatenation(closures, TerminalByContent(Token(')'))))
        closures.from(OrderedChoice(Concatenation(closure, closures), Empty()))

        val string = "(((()))(())())"
        val result = closure.match(tokens(string))

        assertIs<Accepted>(result)
        assert(!result.rest.any())
    }

    @Test
    fun `partially accept recursive grammars matched with a malformed expression`() {
        val memo = MatcherMemo()
        val closures = NonTerminal(memo)
        val closure = Concatenation(TerminalByContent(Token('(')), Concatenation(closures, TerminalByContent(Token(')'))))
        closures.from(OrderedChoice(Concatenation(closure, closures), Empty()))

        val string = "((()))(())())"
        val result = closure.match(tokens(string))

        assertIs<Accepted>(result)
        assert(result.rest.any())
    }

    @Test
    fun `accept a left recursive grammars matched with an expression`() {
        val memo = MatcherMemo()
        val operand = TerminalByContent(Token('e'))
        val operator = TerminalByContent(Token('+'))
        val sum = NonTerminal(memo)
        sum.from(OrderedChoice(Concatenation(sum, Concatenation(operator, operand)), operand))

        val string = "e+e+e+e+e+e+e+e+e+e"
        val result = sum.match(tokens(string))

        assertIs<Accepted>(result)
        assert(!result.rest.any())
    }

    @Test
    fun `parse with a grammar with multiple left recursions accepting a complex input`() {
        val grammar = baabGrammar()
        val string = "baaaabaaaaaab"
        val result = grammar.match(tokens(string))

        assertIs<Accepted>(result)
        assert(!result.rest.any())
    }

    private fun baabGrammar(): NonTerminal {
        val memo = MatcherMemo()
        val a = TerminalByContent(Token('a'))
        val b = TerminalByContent(Token('b'))
        val start = NonTerminal(memo)
        val bigA = NonTerminal(memo)

        start.from(OrderedChoice(Concatenation(bigA, b), b))
        bigA.from(OrderedChoice(Concatenation(bigA, a), Concatenation(start, a)))
        return start
    }
}