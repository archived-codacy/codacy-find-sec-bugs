 The input values included in SQL queries need to be passed in safely. Bind variables in prepared statements can be used to easily mitigate the risk of SQL injection.

**Vulnerable Code:**

```
EntityManager pm = getEM();

TypedQuery<UserEntity> q = em.createQuery(
    String.format("select * from Users where name = %s", username),
    UserEntity.class);

UserEntity res = q.getSingleResult();
```

**Solution:**

```
TypedQuery<UserEntity> q = em.createQuery(
    "select * from Users where name = usernameParam",UserEntity.class)
    .setParameter("usernameParam", username);

UserEntity res = q.getSingleResult();
```
  

**References (JPA)**  
[The Java EE 6 Tutorial: Creating Queries Using the Java Persistence Query Language](http://docs.oracle.com/javaee/6/tutorial/doc/bnbrg.html)  
**References (SQL injection)**  
[WASC-19: SQL Injection](http://projects.webappsec.org/w/page/13246963/SQL%20Injection)  
[CAPEC-66: SQL Injection](http://capec.mitre.org/data/definitions/66.html)  
[CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')](http://cwe.mitre.org/data/definitions/89.html)  
[OWASP: Top 10 2013-A1-Injection](https://www.owasp.org/index.php/Top_10_2013-A1-Injection)  
[OWASP: SQL Injection Prevention Cheat Sheet](https://www.owasp.org/index.php/SQL_Injection_Prevention_Cheat_Sheet)  
[OWASP: Query Parameterization Cheat Sheet](https://www.owasp.org/index.php/Query_Parameterization_Cheat_Sheet)

