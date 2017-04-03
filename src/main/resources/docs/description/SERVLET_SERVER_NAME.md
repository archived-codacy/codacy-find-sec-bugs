 The hostname header can be controlled by the client. As such, its value should not be used in any security critical decisions. Both `ServletRequest.getServerName()` and `HttpServletRequest.getHeader("Host")` have the same behavior which is to extract the `Host` header.
```
GET /testpage HTTP/1.1
Host: www.example.com
[...]
```

The web container serving your application may redirect requests to your application by default. This would allow a malicious user to place any value in the Host header. It is recommended that you do not trust this value in any security decisions you make with respect to a request.

  

**Reference**  
[CWE-807: Untrusted Inputs in a Security Decision](http://cwe.mitre.org/data/definitions/807.html)

