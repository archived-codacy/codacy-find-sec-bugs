 Just like SQL, all inputs passed to an LDAP query need to be passed in safely. Unfortunately, LDAP doesn't have prepared statement interfaces like SQL. Therefore, the primary defense against LDAP injection is strong input validation of any untrusted data before including it in an LDAP query.

**Code at risk:**

```
NamingEnumeration<SearchResult> answers = context.search("dc=People,dc=example,dc=com",
        "(uid=" + username + ")", ctrls);
```
  

**References**  
[WASC-29: LDAP Injection](http://projects.webappsec.org/w/page/13246947/LDAP%20Injection)  
[OWASP: Top 10 2013-A1-Injection](https://www.owasp.org/index.php/Top_10_2013-A1-Injection)  
[CWE-90: Improper Neutralization of Special Elements used in an LDAP Query ('LDAP Injection')](http://cwe.mitre.org/data/definitions/90.html)  
[LDAP Injection Guide: Learn How to Detect LDAP Injections and Improve LDAP Security](http://www.veracode.com/security/ldap-injection)

