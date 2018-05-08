package wildfly.cache.example


import grails.rest.*
import grails.converters.*

class TimestampController {

	static responseFormats = ['json']

    TimestampService timestampService

    def index() {

        def cached = timestampService.cacheDate()
        def now = new Date()
        def diff = now.getTime() - cached.getTime()
        respond ([cache: cached, now: now, diff: diff])
    }
}
