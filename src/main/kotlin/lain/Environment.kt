package lain

// maps identifiers to values
class Environment(private val parent: Environment? = null) {
    private val env = mutableMapOf<String, RuntimeValue>()

    fun set(name: String, value: RuntimeValue) {
        env[name] = value
    }

    fun get(name: String): RuntimeValue? {
        return when {
            env.containsKey(name) -> {
                env[name]
            }
            parent == null -> {
                error("Valuue does not exist, $name")
            }
            else -> {
                parent.get(name)
            }
        }
    }
}