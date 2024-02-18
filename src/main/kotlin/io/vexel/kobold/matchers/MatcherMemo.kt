import io.vexel.kobold.Accepted
import io.vexel.kobold.Result
import io.vexel.kobold.matchers.NonTerminal
import io.vexel.kobold.matchers.State
import io.vexel.kobold.matchers.Tokens

class MatcherMemo {
    private val states = hashMapOf<Parameters, State>()
    private val detectingRecursion = hashMapOf<Parameters, Boolean>()

    fun initializeFor(symbol: NonTerminal, rest: Tokens) {
        val parameters = Parameters(symbol, rest)
        states[parameters] = State(Accepted(emptySequence(), rest), false)
        detectingRecursion[parameters] = true
    }

    fun getFor(symbol: NonTerminal, rest: Tokens) =
        states[Parameters(symbol, rest)]

    fun isDetectingRecursion(symbol: NonTerminal, rest: Tokens) =
        detectingRecursion[Parameters(symbol, rest)] ?: false

    fun updateFor(symbol: NonTerminal, rest: Tokens, result: Result? = null, grow: Boolean? = null): State {
        val parameters = Parameters(symbol, rest)
        val newState = updatedState(states[parameters]!!, result, grow)
        states[parameters] = newState

        if (result != null)
            detectingRecursion[parameters] = false

        return newState
    }

    private fun updatedState(state: State, result: Result?, grow: Boolean?): State =
        State(result ?: state.result, grow ?: state.grow)
}

data class Parameters(val symbol: NonTerminal, val rest: Tokens)
