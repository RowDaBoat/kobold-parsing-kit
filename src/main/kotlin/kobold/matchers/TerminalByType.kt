package kobold.matchers

import kobold.Accepted
import kobold.Rejected
import kobold.Result

class TerminalByType<T>(private val klass :Class<T>) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): Result {
        val position = tokens.count() - rest.count()

        return when (rest.any() && tokens[position].javaClass == klass) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[position])
            false -> Rejected(rest.firstOrNothing(), rest)
        }
    }
}