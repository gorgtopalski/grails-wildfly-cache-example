package wildfly.cache.example

class Time implements Serializable
{

    String name
    Date date

    static constraints = {
        name nullable: false
        date nullable: false
    }
}
