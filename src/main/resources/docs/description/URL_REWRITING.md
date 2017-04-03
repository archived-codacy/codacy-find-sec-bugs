 The implementation of this method includes the logic to determine whether the session ID needs to be encoded in the URL.  
URL rewriting has significant security risks. Since session ID appears in the URL, it may be easily seen by third parties. Session ID in the URL can be disclosed in many ways, for example:  

- Log files,
- The browser history,
- By copy-and-pasting it into an e-mail or posting,
- The HTTP Referrer.

**Vulnerable Code:**

```
out.println("Click <a href=" + 
                res.encodeURL(HttpUtils.getRequestURL(req).toString()) + 
                ">here</a>");
```

**Solution:**  
Avoid using those methods. If you are looking to encode a URL String or form parameters do not confuse the URL rewriting methods with the URLEncoder class.

  

**References**  
[OWASP Top 10 2010-A3-Broken Authentication and Session Management](https://www.owasp.org/index.php/Top_10_2010-A3-Broken_Authentication_and_Session_Management)

 