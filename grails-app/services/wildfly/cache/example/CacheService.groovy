package wildfly.cache.example

import grails.plugin.cache.Cacheable

class CacheService {

    def index()
    {
        Key.list().size()
    }

    @Cacheable("value")
    def get(String id)
    {
        def key = Key.findByKey(id)

        if (key != null)
        {
            Value.findByKey(key)
        }
        else {
            [key: id, value: null]
        }
    }

    def save(String id, Object v)
    {
        def key = Key.findByKey(id)

        if (key == null) {

            key = new Key(key: id).save()
            new Value(value: v, key: key).save(flush:true)
            true
        }
        else false
    }

    def update(String id, Object v)
    {
        def key = Key.findByKey(id)

        if (key != null)
        {
            def value = Value.findByKey(key)
            value.value = v
            value.save(flush:true)
            true
        }
        else false
    }

    def delete(String id)
    {
        def key = Key.findByKey(id)

        if (key)
        {
            Value.findByKey(key).delete()
            key.delete(flush: true)
            true
        }
        else false
    }
}
