 When an HTTP request contains unexpected CR and LF characters, the server may respond with an output stream that is interpreted as two different HTTP responses (instead of one). An attacker can control the second response and mount attacks such as cross-site scripting and cache poisoning attacks. According to OWASP, the issue has been fixed in virtually all modern Java EE application servers, but it is still better to validate the input. If you are concerned about this risk, you should test on the platform of concern to see if the underlying platform allows for CR or LF characters to be injected into headers. This weakness is reported with lower priority than SQL injection etc., if you are using a vulnerable platform, please check low-priority warnings too.  

**Code at risk:**

```
String author = request.getParameter(AUTHOR_PARAMETER);
// ...
Cookie cookie = new Cookie("author", author);
response.addCookie(cookie);
```
  

**References**  
[OWASP: HTTP Response Splitting](https://www.owasp.org/index.php/HTTP_Response_Splitting)  
[CWE-113: Improper Neutralization of CRLF Sequences in HTTP Headers ('HTTP Response Splitting')](http://cwe.mitre.org/data/definitions/113.html)[CWE-93: Improper Neutralization of CRLF Sequences ('CRLF Injection')](http://cwe.mitre.org/data/definitions/93.html)

