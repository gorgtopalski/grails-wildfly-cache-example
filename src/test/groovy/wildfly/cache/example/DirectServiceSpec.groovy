package wildfly.cache.example

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class DirectServiceSpec extends Specification implements ServiceUnitTest<DirectService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
