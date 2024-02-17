package kobold.matchers

import kobold.Rejected

class None : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        Rejected(rest.firstOrNothing(), rest)
}
