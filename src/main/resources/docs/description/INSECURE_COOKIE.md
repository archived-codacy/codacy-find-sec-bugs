 A new cookie is created without the `Secure` flag set. The `Secure` flag is a directive to the browser to make sure that the cookie is not sent for insecure communication (`http://`).

**Code at risk:**

```
Cookie cookie = new Cookie("userName",userName);
response.addCookie(cookie);
```

**Solution (Specific configuration):**

```
Cookie cookie = new Cookie("userName",userName);
cookie.setSecure(true); // Secure flag
cookie.setHttpOnly(true);
```

**Solution (Servlet 3.0 configuration):**

```
<web-app xmlns="http://java.sun.com/xml/ns/javaee" version="3.0">
[...]
<session-config>
 <cookie-config>
  <http-only>true</http-only>
  <secure>true</secure>
 </cookie-config>
</session-config>
</web-app>
```
  

**Reference**  
[CWE-614: Sensitive Cookie in HTTPS Session Without 'Secure' Attribute](https://cwe.mitre.org/data/definitions/614.html)  
[CWE-315: Cleartext Storage of Sensitive Information in a Cookie](https://cwe.mitre.org/data/definitions/315.html)  
[CWE-311: Missing Encryption of Sensitive Data](https://cwe.mitre.org/data/definitions/311.html)  
[OWASP: Secure Flag](https://www.owasp.org/index.php/SecureFlag)  
[Rapid7: Missing Secure Flag From SSL Cookie](https://www.rapid7.com/db/vulnerabilities/http-cookie-secure-flag)

