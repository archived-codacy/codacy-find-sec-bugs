 The input values included in SQL queries need to be passed in safely. Bind variables in prepared statements can be used to easily mitigate the risk of SQL injection. Alternatively to prepare statements, Hibernate Criteria can be used.

**Vulnerable Code:**

```
Session session = sessionFactory.openSession();
Query q = session.createQuery("select t from UserEntity t where id = " + input);
q.execute();
```

**Solution:**

```
Session session = sessionFactory.openSession();
Query q = session.createQuery("select t from UserEntity t where id = :userId");
q.setString("userId",input);
q.execute();
```

**Solution for dynamic queries (with Hibernate Criteria):**

```
Session session = sessionFactory.openSession();
Query q = session.createCriteria(UserEntity.class)
    .add( Restrictions.like("id", input) )
    .list();
q.execute();
```
  

**References (Hibernate)**  
[Hibernate Documentation: Query Criteria](https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/querycriteria.html)  
[Hibernate Javadoc: Query Object](https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/Query.html)  
[HQL for pentesters](http://blog.h3xstream.com/2014/02/hql-for-pentesters.html): Guideline to test if the suspected code is exploitable.  
**References (SQL injection)**  
[WASC-19: SQL Injection](http://projects.webappsec.org/w/page/13246963/SQL%20Injection)  
[CAPEC-66: SQL Injection](http://capec.mitre.org/data/definitions/66.html)  
[CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')](http://cwe.mitre.org/data/definitions/89.html)  
[OWASP: Top 10 2013-A1-Injection](https://www.owasp.org/index.php/Top_10_2013-A1-Injection)  
[OWASP: SQL Injection Prevention Cheat Sheet](https://www.owasp.org/index.php/SQL_Injection_Prevention_Cheat_Sheet)  
[OWASP: Query Parameterization Cheat Sheet](https://www.owasp.org/index.php/Query_Parameterization_Cheat_Sheet)

