 A potential XSS was found. It could be used to execute unwanted JavaScript in a client's browser. (See references)

**Vulnerable Code:**

```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:out value="${param.test_param}" escapeXml="false"/>
```

**Solution:**

```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:out value="${param.test_param}"/>
```
  

**References**  
[WASC-8: Cross Site Scripting](http://projects.webappsec.org/w/page/13246920/Cross%20Site%20Scripting)  
[OWASP: XSS Prevention Cheat Sheet](https://www.owasp.org/index.php/XSS_%28Cross_Site_Scripting%29_Prevention_Cheat_Sheet)  
[OWASP: Top 10 2013-A3: Cross-Site Scripting (XSS)](https://www.owasp.org/index.php/Top_10_2013-A3-Cross-Site_Scripting_%28XSS%29)  
[CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')](http://cwe.mitre.org/data/definitions/79.html)  
[JSTL Javadoc: Out tag](http://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/c/out.html)

 