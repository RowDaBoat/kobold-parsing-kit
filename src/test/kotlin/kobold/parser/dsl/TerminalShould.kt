package kobold.parser.dsl

import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.matchers.Token
import kobold.parser.parser
import org.junit.jupiter.api.Test

class TerminalShould {
    class SomeThing : Token("")
    class OtherThing : Token("")

    @Test
    fun `accept a terminal symbol by type`() {
        val parser = parser {
            val some = terminal<SomeThing>()
            val other = terminal<OtherThing>()
            some then other then some
        }

        val result = parser.parse(listOf(SomeThing(), OtherThing(), SomeThing()))
        println(result)
        assert(result is Accepted)
    }

    @Test
    fun `reject a terminal not matching type`() {
        val parser = parser { terminal<SomeThing>() }

        val result = parser.parse(listOf(OtherThing()))

        assert(result is Rejected)
    }
}