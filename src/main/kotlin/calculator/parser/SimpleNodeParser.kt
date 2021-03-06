package calculator.parser

import calculator.calculator.Node
import calculator.calculator.OperatorString
import java.util.regex.Pattern

class SimpleNodeParser : NodeParser {
    override fun parse(text: String): List<Node> {
        return text
            .split(Regex(REGEX_FOR_NODE))
            .trimAll()
            .exceptEmpty()
            .convertAllToNode()
    }

    fun List<String>.trimAll() = map { it.trim() }

    fun List<String>.exceptEmpty() = filter { it.isNotEmpty() }

    fun String.convertToNode(): Node {
        takeIf {
            Pattern.compile(PATTERN_FOR_NUMBER)
                .matcher(it).matches()
        }?.let {
            return Node.Number(it.toDouble())
        }

        this.takeIf {
            OperatorString.values().any { enum ->
                enum.string == this
            }
        }?.let {
            return OperatorString.values().find { op -> op.string == it }!!.operator
        }

        throw NodeParser.Error.InvalidCharacter(this)
    }

    fun List<String>.convertAllToNode() = map { it.convertToNode() }

    companion object {
        private const val REGEX_FOR_NODE = "((?<=([-*/+\\s]))|(?=([-*/+\\s])))"
        private const val PATTERN_FOR_NUMBER = "^[0-9]+$"
    }
}
