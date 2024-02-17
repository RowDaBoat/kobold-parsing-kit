package kobold.matchers

import kobold.Accepted
import kobold.Rejected
import kobold.lexer.dsl.NothingToken

class Any : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (rest.any()) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[tokens.count() - rest.count()])
            false -> Rejected(NothingToken(), rest)
        }
}
