import kobold.matchers.*

class MatcherMemo {
    private val states: HashMap<Parameters, State> = hashMapOf()

    fun initializeFor(symbol: NonTerminal, rest: Tokens) {
        states[Parameters(symbol, rest)] = State(DetectingRecursion(rest), false)
    }

    fun getFor(symbol: NonTerminal, rest: Tokens) =
        states[Parameters(symbol, rest)]

    fun updateFor(symbol: NonTerminal, rest: Tokens, result: MatcherResult? = null, grow: Boolean? = null): State {
        val parameters = Parameters(symbol, rest)
        val newState = updatedState(states[parameters]!!, result, grow)
        states[parameters] = newState
        return newState
    }

    private fun updatedState(state: State, result: MatcherResult?, grow: Boolean?): State =
        State(result ?: state.result, grow ?: state.grow)
}

data class Parameters(val symbol: NonTerminal, val rest: Tokens)
