package io.vexel.kobold.lexer.rules

import io.vexel.kobold.Token

class RuleMatched(val token: Token, val rest: String) : RuleResult