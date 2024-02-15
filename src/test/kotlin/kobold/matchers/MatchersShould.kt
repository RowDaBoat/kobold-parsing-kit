package kobold.matchers

import MatcherMemo
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MatchersShould {
    @Test
    fun acceptEmpty() {
        val grammar = Empty()
        val result = grammar.match(tokens(""))
        assert(result is Accepted)
    }

    @Test
    fun acceptAMatchingTerminal() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens("("))
        assert(result is Accepted)
    }

    @Test
    fun rejectANonMatchingTerminal() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens(")"))
        assert(result is Rejected)
    }

    @Test
    fun acceptAMatchingConcatenation() {
        val grammar = Concatenation(TerminalByContent(Token('(')), TerminalByContent(Token(')')))
        val result = grammar.match(tokens("()"))
        assert(result is Accepted)
    }

    @Test
    fun rejectANonMatchingConcatenation() {
        val grammar = Concatenation(TerminalByContent(Token('(')), TerminalByContent(Token(')')))
        val result = grammar.match(tokens("))"))
        assert(result is Rejected)
    }

    @Test
    fun acceptAnOrderedChoiceMatchingFirst() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("a"))
        assert(result is Accepted)
    }

    @Test
    fun acceptAnOrderedChoiceMatchingSecond() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("b"))
        assert(result is Accepted)
    }

    @Test
    fun rejectAnOrderedChoiceNotMatchingEither() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')));
        val result = grammar.match(tokens("c"))
        assert(result is Rejected)
    }

    @Test
    fun acceptZeroOrMoreRepetitions() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(OrderedChoice(Concatenation(TerminalByContent(Token('a')), manyAs), Empty()))

        val result = manyAs.match(tokens("aaaaaaaaaaaaaaaaa"))

        assert(result is Accepted)
    }

    @Test
    fun rejectZeroOrMoreRepetitionsEndingWithOtherCharacter() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(OrderedChoice(Concatenation(TerminalByContent(Token('a')), manyAs), Empty()))

        val string = "aaaaaaaaaaaaaaaab"
        val result = manyAs.match(tokens(string))

        assert(result is Accepted)
        assertEquals(1, result.rest.count())
    }

    @Test
    fun rejectZeroOrMoreRepetitionsStartingWithOtherCharacter() {
        val memo = MatcherMemo()
        val manyAs = NonTerminal(memo)
        manyAs.from(OrderedChoice(Concatenation(TerminalByContent(Token('a')), manyAs), Empty()))

        val result = manyAs.match(tokens("baaaaaaaaaaaaaaaa"))

        assert(result is Accepted)
        assert(!result.matched.any())
    }

    @Test
    fun acceptRecursiveGrammarsMatchedWithANestedExpression() {
        val memo = MatcherMemo()
        val closures = NonTerminal(memo)
        val closure = Concatenation(TerminalByContent(Token('(')), Concatenation(closures, TerminalByContent(Token(')'))))
        closures.from(OrderedChoice(Concatenation(closure, closures), Empty()))

        val string = "(((()))(())())"
        val result = closure.match(tokens(string))

        assert(result is Accepted)
        assert(!result.rest.any())
    }

    @Test
    fun rejectRecursiveGrammarsMatchedWithAMalformedExpression() {
        val memo = MatcherMemo()
        val closures = NonTerminal(memo)
        val closure = Concatenation(TerminalByContent(Token('(')), Concatenation(closures, TerminalByContent(Token(')'))))
        closures.from(OrderedChoice(Concatenation(closure, closures), Empty()))

        val string = "((()))(())())"
        val result = closure.match(tokens(string))

        assert(result is Accepted)
        assert(result.rest.any())
    }

    @Test
    fun acceptALeftRecursiveGrammarsMatchedWithAnExpression() {
        val memo = MatcherMemo()
        val operand = TerminalByContent(Token('e'))
        val operator = TerminalByContent(Token('+'))
        val sum = NonTerminal(memo)
        sum.from(OrderedChoice(Concatenation(sum, Concatenation(operator, operand)), operand))

        val string = "e+e+e+e+e+e+e+e+e+e"
        val result = sum.match(tokens(string))

        assert(result is Accepted)
        assert(!result.rest.any())
    }

    @Test
    fun parseWithAGrammarWithMultipleLeftRecursionsAcceptingAComplexInput() {
        val grammar = baabGrammar();
        val string = "baaaabaaaaaab"
        val result = grammar.match(tokens(string))

        assert(result is Accepted)
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