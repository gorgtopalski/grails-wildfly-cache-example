package wildfly.cache.example

import grails.plugin.cache.Cacheable

class TimestampService {

    @Cacheable('time')
    Time cacheDate(String name)
    {
        Time.findByName(name)
    }
}
