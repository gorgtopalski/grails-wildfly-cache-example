package wildfly.cache.example

class Key {

    String key

    static constraints = {
        key unique: true, nullable: false, blank: false
    }

    @Override
    String toString() {
        return key
    }
}
