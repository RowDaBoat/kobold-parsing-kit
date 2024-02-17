package kobold.lexer.rules

import kobold.matchers.Token

class RuleMatched(val token: Token, val rest: String) : RuleResult