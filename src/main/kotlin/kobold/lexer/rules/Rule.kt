package kobold.lexer.rules

interface Rule {
    fun match(string: String): RuleResult
}
