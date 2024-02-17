package kobold.parser.dsl.support

import kobold.matchers.TerminalByContent
import kobold.matchers.Token

fun tokens(string: String) = string.map { Token(it.toString()) }

fun tokenMatchers(string: String) = tokens(string).map { TerminalByContent(it) }
