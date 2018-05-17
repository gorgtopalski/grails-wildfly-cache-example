package wildfly.cache.example

class BootStrap {

    def init = { servletContext ->
        new Time(name: "today", date: new Date()).save()
    }

    def destroy = {

    }
}
