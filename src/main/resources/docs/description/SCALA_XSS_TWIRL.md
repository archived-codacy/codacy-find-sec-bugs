 A potential XSS was found. It could be used to execute unwanted JavaScript in a client's browser. (See references)

**Vulnerable Code:**

```
@(value: Html)

@value
```

**Solution:**

```
@(value: String)

@value
```

The best defense against XSS is context sensitive output encoding like the example above. There are typically 4 contexts to consider: HTML, JavaScript, CSS (styles), and URLs. Please follow the XSS protection rules defined in the OWASP XSS Prevention Cheat Sheet, which explains these defenses in significant detail.

  

**References**  
[WASC-8: Cross Site Scripting](http://projects.webappsec.org/w/page/13246920/Cross%20Site%20Scripting)  
[OWASP: XSS Prevention Cheat Sheet](https://www.owasp.org/index.php/XSS_%28Cross_Site_Scripting%29_Prevention_Cheat_Sheet)  
[OWASP: Top 10 2013-A3: Cross-Site Scripting (XSS)](https://www.owasp.org/index.php/Top_10_2013-A3-Cross-Site_Scripting_%28XSS%29)  
[CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')](http://cwe.mitre.org/data/definitions/79.html)  
[OWASP Java Encoder](https://code.google.com/p/owasp-java-encoder/)

 