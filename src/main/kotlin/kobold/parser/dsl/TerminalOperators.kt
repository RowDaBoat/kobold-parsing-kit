package kobold.parser.dsl

import kobold.matchers.Matcher
import kobold.matchers.TerminalByType

interface TerminalOperatorDSL {
    fun<T> terminal(type: Class<T>): Matcher
}

class TerminalOperators : TerminalOperatorDSL {
    override fun<T> terminal(type: Class<T>) =
        TerminalByType(type)
}

inline fun<reified T> ParserDSL.terminal() =
    terminal(T::class.java)
