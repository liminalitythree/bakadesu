package lain

sealed class RuntimeValue

class RangeListValue(val range: RangeList) : RuntimeValue()

class FunctionValue(val sexpr: Sexpr, val args: List<String>) : RuntimeValue()

abstract class BuiltinFunction(val interpreter: Interpreter) : RuntimeValue() {
    abstract fun execute(list: BList): RangeList
}
