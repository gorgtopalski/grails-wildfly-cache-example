package wildfly.cache.example

class DirectController {
	static responseFormats = ['json', 'xml']
    def grailsCacheManager

    def index() {

        def cache = grailsCacheManager.getCache('direct')

        def value

        try {
            value = cache.get('key').get()
        } catch(Exception ex)
        { log.error(ex.message) }


        if (value == null)
        {
            value = new Date()
            cache.put('key',value)

            respond([cached: 'miss', key: 'key', value: value])
        }
        else {
            respond([cached: 'hit', key: 'key', value: value])
        }
    }
}
