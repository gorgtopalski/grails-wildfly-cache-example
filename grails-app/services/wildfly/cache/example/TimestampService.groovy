package wildfly.cache.example

import grails.plugin.cache.Cacheable

class TimestampService {

    @Cacheable('date')
    def cacheDate()
    {
        def date = new Date()
        return date
    }

}
