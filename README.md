## Grails Wildfly Cache Example

An example Grails app that uses the inner Infinispan module of a Wildfly 10 server cluster.

## Custom-Cache Plugin
For the example to work, you must download the [custom-cache](https://github.com/gorgtopalski/grails-cache-custom) plugin for grails, publish it to your local maven repository and declare it as a dependency in your grails application.

- [ ] Download the plugin
```
git clone https://github.com/gorgtopalski/grails-cache-custom
```
- [ ] Publish to the local maven repository
```
cd grails-cache-custom
./gradlew publishToMavenLocal
```
- [ ] Download the example application
```
cd .. 
git clone https://github.com/gorgtopalski/grails-wildfly-cache-example
```
- [ ] Generate the WAR file
```
cd grails-wildfly-cache-example
./grailsw clean
./grailsw war
```
- [ ] Create a new Cache Container in the Wildfly 10 Cluster
```
Go to your Wildfly 10 Administration Console -> Configuration -> Subsystems -> Infinispan -> Add
Fill the requested data.
```
In this example, the used Cache Container is called "test". It can be changed in the [application.yml](https://github.com/gorgtopalski/grails-wildfly-cache-example/blob/master/grails-app/conf/application.yml) file.

- [ ] Deploy to the Wildfly 10 cluster
```
Go to your Wildfly 10 Administration Console -> Deployments --> Add --> Select the generated WAR file from the previous step.
(the WAR file should be in the grails-wildfly-cache-example/build/libs/ folder)
```
- [ ] Access the application
```
"wildfly-host:port"/cache-example
```


## Grails application
The provided application is a simple Grails App generated with the grails rest-api profile. It provides some REST endpoints where the plugin can be shown in action.

#### Direct

The direct endpoint interacts directly with the cache system. It injects the GrailsCacheManager at runtime. It tries to get the corresponding value for a given key. If there isn't a value present in the cache, it will store a value inside for future usage.

> GET /cache-example/direct

The fisrt time response will be marked as "miss"

```
{
	"cached": "miss",
	"key": "key",
	"value": "2018-05-17T19:43:50Z"
}
```

After that, once a value is stored in the cache. The response will be marked as "hit"
```
{
	"cached": "hit",
	"key": "key",
	"value": "2018-05-17T19:43:50Z"
}
```

#### Explorer
The explorer waypoint makes a cache-dump. It queries each cache, for each containg key and dumps the values. 

> GET /cache-example/explorer

```
{
	"cache": {},
	"grailsBlocksCache": {},
	"grailsTemplatesCache": {},
	"direct": {
		"key": "Thu May 17 19:43:50 UTC 2018"
	},
	"time": {
		"grails.plugin.cache.custom.CustomKeyGenerator$TemporaryGrailsCacheKey@5bd81f8f": "wildfly.cache.example.Time : 1"
	}
}
```


#### Timestamp
The timestamp endpoint will cache a timestamp when accessed for the first time. After that, on each following access, a new timestamp will be generated and will be compared against the one in the cache. "Diff" is the difference in ms between the two timestamps.


> GET /cache-example/timestamp

```
{
	"cache": "2018-05-08T09:54:02Z",
	"now": "2018-05-08T10:05:17Z",
	"diff": 675397
}
```



## Using the Wildfly 10 inner Infinispan cache

To be able to use the plugin in other projects you need to:

- [ ] Add it as a dependency in your build.gradle file
```
dependencies {
	compile 'cat.dipta.plugins:cache-custom:0.1'
    ...
}
```
- [ ] Change the spring-boot-starter-tomcat dependency to a provided
```
dependencies {
	provided "org.springframework.boot:spring-boot-starter-tomcat"
    ...
}
```
- [ ] Enable the plugin in the application.yml file
* The "jdni" parameter must be a valid JDNI URI pointing to the Cache Container created in the Wildfly 10 cluster.
* The "default" parameter must be a valid JDNI URI pointing to the default cache. This cache will be used as a template for future caches created within the plugin.
```
grails:
    cache:
        custom:
            impl: 'wildfly'
            wildfly:
                jdni: "java:jboss/infinispan/container/test"
                default: "java:jboss/infinispan/container/test/cache"
```







