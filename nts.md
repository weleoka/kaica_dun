
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
