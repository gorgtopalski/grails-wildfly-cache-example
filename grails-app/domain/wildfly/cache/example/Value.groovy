package wildfly.cache.example

class Value {

    static belongsTo = [key: Key]
    String value

    static constraints = {
    }

    @Override
    String toString() {
        return value
    }
}
