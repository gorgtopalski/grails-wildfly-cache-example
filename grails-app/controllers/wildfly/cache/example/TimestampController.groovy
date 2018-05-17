package wildfly.cache.example

class TimestampController {

	static responseFormats = ['json']

    TimestampService timestampService

    def index() {

        def cached = timestampService.cacheDate('today')
        def now = new Date()
        def diff = now.getTime() - cached.date.getTime()
        respond ([cache: cached, now: now, diff: diff])
    }
}
