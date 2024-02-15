package kobold.parser.dsl.support

import kobold.matchers.Token
import kobold.matchers.TerminalByContent

fun tokens(string: String) = string.map { Token(it.toString()) }

fun tokenMatchers(string: String) = tokens(string).map { TerminalByContent(it) }
