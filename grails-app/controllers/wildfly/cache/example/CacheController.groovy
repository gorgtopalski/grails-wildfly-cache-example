package wildfly.cache.example

import org.springframework.http.HttpStatus

class CacheController {
    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']
	static responseFormats = ['json']

    CacheService cacheService
	
    def index() {
        respond([stored: cacheService.index()])
    }

    def show(String id) {
        respond(cacheService.get(id))
    }

    def save()
    {
        def key = params.get('key') as String
        def value = params.get('value')

        if (cacheService.save(key,value))
        {
            respond(cacheService.get(key), status: HttpStatus.CREATED)
        }
        else respond([message: 'Not found'], status: 404)
    }

    def update(String id)
    {
        def value = params.get('value')

        if (cacheService.update(id,value))
        {
            respond(cacheService.get(id), status: HttpStatus.ACCEPTED)
        }
        else respond([message: 'Not found'], status: 404)
    }

    def delete(String id)
    {
        if (cacheService.delete(id))
        {
            respond([:], status: 204)
        }
        else {
            respond([message: 'Not found'], status: 404)
        }

    }
}
