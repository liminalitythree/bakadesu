package lain

class DefineFun(interpreter: Interpreter) : BuiltinFunction(interpreter) {
    override fun execute(list: BList): RangeList {
        if (list.list.size != 4) {
            error(13375793, "YTOU DID NOT PROVIDE THE RIGHT NOMUMBER OF ARGUMENTS FOR THE DEFINE FUNCTION YOU IDIOT DUMMY INCBABALEPLE PROOGRAMMER")
        }
        if (list.list[1] !is BList) {
            error(-22332, "uhhh you did something bad u dummy~~~~")
        }
        if (list.list[2] !is BList) {
            error(34, "the 2nd thingie in the thingie should be a list maybe.")
        }

        val arglist = list.list[1] as BList

        val defaultinlist = list.list[2] as BList

        for (a in arglist.list) {
            if (a !is Identifier) {
                error(-934, "ur dumb... dummmmmbbbbbb lololol :)")
            }
        }

        val fnname = (arglist.list[0] as Identifier).name

        val fnargs = arglist.list.subList(1, arglist.list.size).map { (it as Identifier).name }

        val fncontent: Sexpr = list.list[3]

        val oldenv = interpreter.environment

        val fnValue = FunctionValue(fncontent, fnargs)

        interpreter.environment = Environment(interpreter.environment)

        interpreter.environment.set(fnname, fnValue)

        val ret = interpreter.executeFunction(fnValue, defaultinlist)

        interpreter.environment = oldenv

        return ret
    }
}

class UnionFun(interpreter: Interpreter): BuiltinFunction(interpreter) {
    override fun execute(list: BList): RangeList {
        val args = list.list.subList(1, list.list.size).map { interpreter.eval(it) }
        return args.reduce { a, e -> RangeList.union(a, e) }
    }
}

class IntersectionFun(interpreter: Interpreter): BuiltinFunction(interpreter) {
    override fun execute(list: BList): RangeList {
        val args = list.list.subList(1, list.list.size).map { interpreter.eval(it) }
        return args.reduce { a, e -> RangeList.intersection(a, e) }
    }
}

class SetDiffFun(interpreter: Interpreter): BuiltinFunction(interpreter) {
    override fun execute(list: BList): RangeList {
        val args = list.list.subList(1, list.list.size).map { interpreter.eval(it) }
        return args.reduce { a, e -> RangeList.setDiff(a, e) }
    }
}

class SymDiffFun(interpreter: Interpreter): BuiltinFunction(interpreter) {
    override fun execute(list: BList): RangeList {
        val args = list.list.subList(1, list.list.size).map { interpreter.eval(it) }
        return args.reduce { a, e -> RangeList.symmetricDiff(a, e) }
    }
}
