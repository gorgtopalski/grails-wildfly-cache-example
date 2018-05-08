package wildfly.cache.example

class BootStrap {

    def init = { servletContext ->

        def rand = new Random()

        (1..100).each {
            log.info("Generating some data")
            def key = new Key(key: it).save()
            new Value(value: (it * rand.nextDouble()).toString().encodeAsBase64(), key: key).save()
        }
    }

    def destroy = {

    }
}
