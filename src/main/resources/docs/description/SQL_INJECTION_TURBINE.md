 The input values included in SQL queries need to be passed in safely. Bind variables in prepared statements can be used to easily mitigate the risk of SQL injection. Turbine API provide a DSL to build query with Java code.

**Vulnerable Code:**

```
List<Record> BasePeer.executeQuery( "select * from Customer where id=" + inputId );
```

**Solution (using Criteria DSL):**

```
Criteria c = new Criteria();
c.add( CustomerPeer.ID, inputId );

List<Customer> customers = CustomerPeer.doSelect( c );
```
**Solution (using specialized method):**  

```
Customer customer = CustomerPeer.retrieveByPK( new NumberKey( inputId ) );
```
**Solution (using OWASP Encoder):**  

```
import org.owasp.esapi.Encoder;

BasePeer.executeQuery("select * from Customer where id = '"+Encoder.encodeForSQL(inputId)+"'");
```
  

**References (Turbine)**  
[Turbine Documentation: Criteria Howto](https://turbine.apache.org/turbine/turbine-2.1/howto/criteria-howto.html)  
**References (SQL injection)**  
[WASC-19: SQL Injection](http://projects.webappsec.org/w/page/13246963/SQL%20Injection)  
[CAPEC-66: SQL Injection](http://capec.mitre.org/data/definitions/66.html)  
[CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')](http://cwe.mitre.org/data/definitions/89.html)  
[OWASP: Top 10 2013-A1-Injection](https://www.owasp.org/index.php/Top_10_2013-A1-Injection)  
[OWASP: SQL Injection Prevention Cheat Sheet](https://www.owasp.org/index.php/SQL_Injection_Prevention_Cheat_Sheet)  
[OWASP: Query Parameterization Cheat Sheet](https://www.owasp.org/index.php/Query_Parameterization_Cheat_Sheet)

