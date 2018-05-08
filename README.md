## Grails Wildfly Cache Example

An example Grails app that uses the inner Infinispan module of a Wildfly 10 server cluster.

## Custom-Cache Plugin
For the example to work, you must download the [custom-cache](http://https://github.com/gorgtopalski/grails-cache-custom) plugin for grails, publish it to your local maven repository and declare it as a dependency in your grails application.

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
The provided application is a simple Grails App generated with the grails rest-api profile. It provides two REST endpoints where the plugin can be shown in action.

#### Timestamp
The timestamp endpoint, will cache a timestamp when accessed for the first time. After that, on each following access, a new timestamp will be generated and will be compared against the one in the cache. The diff is the difference in ms between the two timestamps.


> GET /cache-example/timestamp

```
{
	"cache": "2018-05-08T09:54:02Z",
	"now": "2018-05-08T10:05:17Z",
	"diff": 675397
}
```

#### Cache

The Cache endpoint is a simple key-value store. When deployed, the application will generate 100 keys with random values.

> GET /cache-example/cache

```
{
	"stored": 100
}
```

Each the corresponding value for each key will be cached after the first access.

> GET /cache-example/cache/71

```
{
	"id": 71,
	"value": "MjEuMDUzOTUyMzgxMzExNjY=",
	"key": {
		"id": 71
	}
}
```
On a local standalone Wildfly 10 cluster:
- When accessing a value for the first time:
	- curl -o /dev/null -s -w %{time_total}\\n  localhost:8080/cache-example/cache/71
	- time: 154ms
- When accessed the same value after the first time:
	- curl -o /dev/null -s -w %{time_total}\\n  localhost:8080/cache-example/cache/71
	- time: 7.45ms

The Cache endpoint also provides the functionality to add, update or delete entries.
> POST /cache-example/cache
> PUT /cache-example/cache/$id
> DELETE /cache-example/cache/$id

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
```
grails:
    cache:
        custom:
            impl: 'wildfly'
            wildfly:
				jdni: "java:jboss/infinispan/container/test"
```
Note: the JDNI parmater must be a valid JDNI URI pointing to the Cache Container created in the Wildfly 10 cluster.






