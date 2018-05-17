package wildfly.cache.example

import grails.plugin.cache.GrailsCache
import groovy.util.logging.Slf4j

@Slf4j
class ExplorerController {
	static responseFormats = ['json', 'xml']

    def grailsCacheManager

    def index() {

        def map = [:]

        def names = grailsCacheManager.getCacheNames()

        names.each { name ->

            log.info("CACHE: $name")

            def contents = [:]

            def cache = (GrailsCache) grailsCacheManager.getCache(name)

            cache.getAllKeys().each { key ->
                def value = cache.get(key).get()
                contents.put(key.toString(),value.toString())
            }
            map.put(name, contents)
        }

        respond map
    }
}
