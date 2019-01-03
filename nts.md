
 https:howtodoinjava.com/spring-core/how-to-use-spring-component-repository-service-and-controller-annotations/
 Always use the annotations over concrete classes; not over interfaces.

 @Component - marks a java class as a bean so the component-scanning mechanism of spring can pick
 it up and pull it into the application context. @Component used to auto-detect and auto-configure
 beans using classpath scanning. There’s an implicit one-to-one mapping between the
 annotated class and the bean (i.e. one bean per class).

 @Repository - Although above use of @Component is good enough but we can use more suitable annotation that provides
 additional benefits specifically for DAOs i.e. @Repository annotation. The @Repository annotation is
 a specialization of the @Component annotation with similar use and functionality.
 In addition to importing the DAOs into the DI container, it also makes the unchecked exceptions
 (thrown from DAO methods) eligible for translation into Spring DataAccessException.*/

 @Service - is a specialization of the @Component annotation.
 It doesn’t currently provide any additional behavior over the @Component annotation,
 but it’s a good idea to use @Service over @Component in service-layer classes because it specifies intent better.

 @Controller - marks a class as a Spring Web MVC controller.
 It too is a @Component specialization, so beans marked with it are automatically imported into the DI container.
 When we add the @Controller annotation to a class, we can use another
 annotation i.e. @RequestMapping; to map URLs to instance methods of a class.
 We will face very rare situations where we will need to use @Component annotation.
 Most of the time, we will using @Repository, @Service and @Controller annotations. @Component should be used
 when the class does not fall into either of three categories i.e. controller, manager and dao.

 @Bean - is used to explicitly declare a single bean, rather than letting Spring do it automatically for us.
 Another big difference is that @Component is a class level annotation where as @Bean is a method level
 annotation and ,by default, name of the method serves as the bean name.  is used to explicitly declare
 a single bean, rather than letting Spring do it automatically for us.

 @Autowired - allows you to inject directly using fields, setter methods, or constructors.
 If you want final fields autowired, use constructor injection, just like you would do if you did 
 the injection by yourself. Generally speaking, field autowiring is not 
 regarded as good practice and constructor injection is preferred instead. The only case where it is relatively 
 acceptable is within test classes, which do not have constructors. The default scope is singleton, so you will 
 have only one instance of the bean, which is injected in multiple places. If you explicitly define the 
 scope to be "prototype", then multiple instances will exist, possibly lazy (depending on configuration)
 https://www.tutorialspoint.com/spring/spring_autowired_annotation.htm
 https://stackoverflow.com/a/34580087/3092830
 https://stackoverflow.com/a/634754/3092830 - Annotations or XML for bean configuration?

 @Inject - the javax.inject.Inject annotations. @Inject is part of the Java CDI (Contexts and Dependency Injection) 
 standard introduced in Java EE 6 (JSR-299), read more. Spring has chosen to support using @Inject synonymously 
 with their own @Autowired annotation.

 @EnableJpaRepositories(basePackages = "demo_pckg.main")

 @SpringBootApplication is equivalent to @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes.

 @DataJpaTest

 @Indexed

 @Transactional

 @Test

 @PersistenceContext

 @PersistenceUnit

 @Resource

 @EntityScan






## Next to know about Spring
- Why Autowire to the interface rather than an implementing class? How does Spring know the context?
https://stackoverflow.com/questions/12899372/spring-why-do-we-autowire-the-interface-and-not-the-implemented-class


- Read again the reasons for field, constructor and set injection of springs @Autowired annotation.
https://www.tutorialspoint.com/spring/spring_autowired_annotation.htm
https://stackoverflow.com/questions/633158/spring-autowired-usage?rq=1

- What is actually the implementation of @Transactional


```java
    /* - - - Example 1 - Usage of session and sessionFactory

    public void testBasicUsage() {
        // create a couple of events...
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new Event( "Our very first event!", new Date() ) );
        session.save( new Event( "A follow up event", new Date() ) );
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Event" ).list();
        for ( Event event : (List<Event>) result ) {
            System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
        }
        session.getTransaction().commit();
        session.close();
    }



        // - - - Example 2 - Usage of JPA EntityManager
        EntityManager em = null;

        // TM is a class bundled with the example code of Hibernate 5 book.
        UserTransaction tx = TM.getUserTransaction();

        try {
            tx.begin();
            em = JPA.createEntityManager();


            tx.commit();

        } catch (Exception ex) {

            // Transaction rollback, exception handling
            // ...

        } finally {

            if (em != null && em.isOpen())
                em.close(); // You create it, you close it!

        }


        // - - - Example 3 - Seting an item to persist
        Item item = new Item();
        item.setName("Some Item");

        em.persist(item);
        Long ITEM_ID = item.getId();
```

### session.persist(my_object) Vs. session.save(my_object)
persist() is well defined. It makes a transient instance persistent. However, it doesn't guarantee that the identifier value will be assigned to the persistent instance immediately, the assignment might happen at flush time. The spec doesn't say that, which is the problem I have with persist().
persist() also guarantees that it will not execute an INSERT statement if it is called outside of transaction boundaries. This is useful in long-running conversations with an extended Session/persistence context. A method like persist() is required.
save() does not guarantee the same, it returns an identifier, and if an INSERT has to be executed to get the identifier (e.g. "identity" generator, not "sequence"), this INSERT happens immediately, no matter if you are inside or outside of a transaction. This is not good in a long-running conversation with an extended Session/persistence context.


### Detecting entity state using the identifier
Sometimes you need to know whether an entity instance is transient, persistent, or
detached. An entity instance is in persistent state if EntityManager#contains(e)
returns true. It’s in transient state if PersistenceUnitUtil#getIdentifier(e)
returns null. It’s in detached state if it’s not persistent, and Persistence-
UnitUtil#getIdentifier(e) returns the value of the entity’s identifier property.
You can get to the PersistenceUnitUtil from the EntityManagerFactory.

There are two issues to look out for. First, be aware that the identifier value may not
be assigned and available until the persistence context is flushed. Second, Hibernate
(unlike some other JPA providers) never returns null from Persistence-
UnitUtil#getIdentifier() if your identifier property is a primitive (a long and not
a Long).


## JPA notes
JPA also provides a way for building static queries, as named queries, using the @NamedQuery and @NamedQueries annotations.
It is considered to be a good practice in JPA to prefer named queries over dynamic queries when possible.







# Trying to speed up boot-time:
INFO [kaica_dun.App] No active profile set, falling back to default profiles: default
Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.condition.BeanTypeRegistry'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.AutoConfigurationPackages'
Creating shared instance of singleton bean 'propertySourcesPlaceholderConfigurer'
Creating shared instance of singleton bean 'emBeanDefinitionRegistrarPostProcessor'
Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
Creating shared instance of singleton bean 'org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata'
Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionalEventListenerFactory'
Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
Creating shared instance of singleton bean 'org.springframework.context.annotation.internalCommonAnnotationProcessor'
Creating shared instance of singleton bean 'org.springframework.context.annotation.internalPersistenceAnnotationProcessor'
Creating shared instance of singleton bean 'org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor'
Creating shared instance of singleton bean 'org.springframework.aop.config.internalAutoProxyCreator'
Creating shared instance of singleton bean 'dataSourceInitializerPostProcessor'
Creating shared instance of singleton bean 'persistenceExceptionTranslationPostProcessor'
Autowiring by type from bean name 'persistenceExceptionTranslationPostProcessor' via factory method to bean named 'environment'
Creating shared instance of singleton bean 'dataSourceInitializedPublisher'
Creating shared instance of singleton bean 'entityManagerFactory'
Creating shared instance of singleton bean 'org.springframework.transaction.config.internalTransactionAdvisor'
Creating shared instance of singleton bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration'
Creating shared instance of singleton bean 'transactionAttributeSource'
Creating shared instance of singleton bean 'transactionInterceptor'
Creating shared instance of singleton bean 'entityManagerFactoriesCfg'
Creating shared instance of singleton bean 'dataSource'
Creating shared instance of singleton bean 'dataSourceCfg'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker'
Creating shared instance of singleton bean 'spring.datasource-org.springframework.boot.autoconfigure.jdbc.DataSourceProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker' via constructor to bean named 'spring.datasource-org.springframework.boot.autoconfigure.jdbc.DataSourceProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker' via constructor to bean named 'org.springframework.context.annotation.AnnotationConfigApplicationContext@8a589a2'
Creating shared instance of singleton bean 'app'
Creating shared instance of singleton bean 'userServiceImpl'
Creating shared instance of singleton bean 'userInterfaceImpl'
Creating shared instance of singleton bean 'org.springframework.orm.jpa.SharedEntityManagerCreator#0'
Creating shared instance of singleton bean 'userInterface'
Creating shared instance of singleton bean 'jpaMappingContext'
Creating shared instance of singleton bean 'HibernateSessionFactory'
Creating shared instance of singleton bean 'hibernateCfg'
Creating shared instance of singleton bean 'gameServiceImpl'
Creating shared instance of singleton bean 'avatarInterface'
Creating shared instance of singleton bean 'dungeonInterface'
Creating shared instance of singleton bean 'testDb'
Creating shared instance of singleton bean 'menuMain'
Creating shared instance of singleton bean 'menuLoggedIn'
Creating shared instance of singleton bean 'transactionManager'
Creating shared instance of singleton bean 'transactionManagersCfg'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration'
Creating shared instance of singleton bean 'mbeanExporter'
Creating shared instance of singleton bean 'objectNamingStrategy'
Autowiring by type from bean name 'mbeanExporter' via factory method to bean named 'objectNamingStrategy'
Creating shared instance of singleton bean 'mbeanServer'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$CglibAutoProxyConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.aop.AopAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration'
Creating shared instance of singleton bean 'spring.jta-org.springframework.boot.autoconfigure.transaction.jta.JtaProperties'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration$HikariPoolDataSourceMetadataProviderConfiguration'
Creating shared instance of singleton bean 'hikariPoolDataSourceMetadataProvider'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.DataSourceInitializationConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration'
Creating shared instance of singleton bean 'spring.jpa-org.springframework.boot.autoconfigure.orm.jpa.JpaProperties'
Creating shared instance of singleton bean 'spring.jpa.hibernate-org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration' via constructor to bean named 'dataSource'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration' via constructor to bean named 'spring.jpa-org.springframework.boot.autoconfigure.orm.jpa.JpaProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration' via constructor to bean named 'org.springframework.beans.factory.support.DefaultListableBeanFactory@6e0d4a8'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration' via constructor to bean named 'spring.jpa.hibernate-org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties'
Creating shared instance of singleton bean 'platformTransactionManagerCustomizers'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration'
Creating shared instance of singleton bean 'spring.transaction-org.springframework.boot.autoconfigure.transaction.TransactionProperties'
Creating shared instance of singleton bean 'jpaVendorAdapter'
Creating shared instance of singleton bean 'entityManagerFactoryBuilder'
Autowiring by type from bean name 'entityManagerFactoryBuilder' via factory method to bean named 'jpaVendorAdapter'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration'
Creating shared instance of singleton bean 'spring.task.execution-org.springframework.boot.autoconfigure.task.TaskExecutionProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration' via constructor to bean named 'spring.task.execution-org.springframework.boot.autoconfigure.task.TaskExecutionProperties'
Creating shared instance of singleton bean 'taskExecutorBuilder'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration'
Creating shared instance of singleton bean 'monsterInterface'
Creating shared instance of singleton bean 'roomInterface'
Creating shared instance of singleton bean 'itemInterface'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration'
Creating shared instance of singleton bean 'spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration' via constructor to bean named 'spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration'
Creating shared instance of singleton bean 'spring.jdbc-org.springframework.boot.autoconfigure.jdbc.JdbcProperties'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration' via constructor to bean named 'dataSource'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$JdbcTemplateConfiguration' via constructor to bean named 'spring.jdbc-org.springframework.boot.autoconfigure.jdbc.JdbcProperties'
Creating shared instance of singleton bean 'jdbcTemplate'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration$NamedParameterJdbcTemplateConfiguration'
Creating shared instance of singleton bean 'namedParameterJdbcTemplate'
Autowiring by type from bean name 'namedParameterJdbcTemplate' via factory method to bean named 'jdbcTemplate'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration'
Creating shared instance of singleton bean 'taskSchedulerBuilder'
Creating shared instance of singleton bean 'spring.task.scheduling-org.springframework.boot.autoconfigure.task.TaskSchedulingProperties'
Autowiring by type from bean name 'taskSchedulerBuilder' via factory method to bean named 'spring.task.scheduling-org.springframework.boot.autoconfigure.task.TaskSchedulingProperties'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration$DataSourceTransactionManagerConfiguration'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration$DataSourceTransactionManagerConfiguration' via constructor to bean named 'dataSource'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration'
Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration$TransactionTemplateConfiguration'
Autowiring by type from bean name 'org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration$TransactionTemplateConfiguration' via constructor to bean named 'transactionManager'
Creating shared instance of singleton bean 'transactionTemplate'
INFO [kaica_dun.App] Started App in 9.633 seconds (JVM running for 13.054)




Example import and then disable autoconfiguration:
@Import({
        DispatcherServletAutoConfiguration.class,
        EmbeddedServletContainerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        JmxAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        ServerPropertiesAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
})

