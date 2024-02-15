package kobold.matchers

import MatcherMemo

typealias Tokens = Sequence<Token>
typealias Evaluator = (Matcher, Tokens) -> MatcherResult

class NonTerminal(private val memo: MatcherMemo, private val producer: ((List<Symbol>) -> Symbol)? = null) : Matcher {
    private var expression: Matcher = Empty()

    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        buildTree(when (val result = memoizedResultFor(rest)) {
            null -> justMatch(tokens, rest, evaluate)
            is DetectingRecursion -> detectRecursion(rest)
            else -> result
        })

    private fun buildTree(result: MatcherResult) =
        when(result) {
            is Accepted -> result.withProducer(producer)
            else -> result
        }

    fun from(expression: Matcher): Matcher {
        this.expression = expression
        return this
    }
    private fun memoizedResultFor(rest: Tokens) =
        memo.getFor(this, rest)?.result


    private fun justMatch(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): MatcherResult {
        memo.initializeFor(this, rest)
        val result = evaluate(expression, rest)
        val preGrowState = memo.updateFor(this, rest, result = result)

        return if (preGrowState.grow)
            growParsingSeed(tokens, rest, result.matched, evaluate)
        else
            result
    }

    private fun detectRecursion(rest: Tokens): MatcherResult =
        memo.updateFor(this, rest, result = Rejected(rest), grow = true).result

    private fun growParsingSeed(
        tokens: List<Token>,
        rest: Tokens,
        alreadyMatched: Tokens,
        evaluate: Evaluator
    ) : MatcherResult {
        while(true) {
            val result = growEvaluate(expression, rest, rest, hashSetOf(this), tokens, evaluate)

            if (result !is Accepted || result.matched.count() <= alreadyMatched.count())
                break

            memo.updateFor(this, rest, result = result)
        }

        return memo.updateFor(this, rest, grow = false).result
    }

    private fun growEvaluate(
        expression: Matcher,
        rest: Tokens,
        current: Tokens,
        limits: HashSet<NonTerminal>,
        tokens: List<Token>,
        evaluate: Evaluator
    ): MatcherResult {
        return if (expression is NonTerminal && rest.count() == current.count() && !limits.contains(expression))
            expression.matchGrow(tokens, rest, limits, evaluate)
        else if (expression is NonTerminal)
            expression.match(tokens, rest, evaluate)
        else
            expression.match(tokens, rest) { it, pos -> growEvaluate(it, pos, current, limits, tokens, evaluate) }
    }

    private fun matchGrow(
        tokens: List<Token>,
        rest: Tokens,
        limits: HashSet<NonTerminal>,
        evaluate: Evaluator
    ): MatcherResult {
        limits.add(this)
        val result = growEvaluate(expression, rest, rest, limits, tokens, evaluate)
        val state = memo.getFor(this, rest)!!

        if (result !is Accepted || result.matched.count() <= state.result.matched.count())
            return state.result

        memo.updateFor(this, rest, result = result)
        return result
    }
}
