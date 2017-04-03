 An implementation of `HttpServletRequestWrapper` called `XSSRequestWrapper` was published through various blog sites. [[1]](http://java.dzone.com/articles/stronger-anti-cross-site)[[2]](http://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html)

The filtering is weak for a few reasons:

- It covers only parameters not headers and side-channel inputs
- The replace chain can be bypassed easily (see example below)
- It's a black list of very specific bad patterns (rather than a white list of good/valid input)

**Example of bypass:**

```
<scrivbscript:pt>alert(1)</scrivbscript:pt>
```

The previous input will be transformed into **`"<script>alert(1)</script>"`**. The removal of `"vbscript:"` is after the replacement of `"<script>.*</script>"`.

For stronger protection, choose a solution that encodes characters automatically in the **view** (template, jsp, ...) following the XSS protection rules defined in the OWASP XSS Prevention Cheat Sheet.

  

**References**  
[WASC-8: Cross Site Scripting](http://projects.webappsec.org/w/page/13246920/Cross%20Site%20Scripting)  
[OWASP: XSS Prevention Cheat Sheet](https://www.owasp.org/index.php/XSS_%28Cross_Site_Scripting%29_Prevention_Cheat_Sheet)  
[OWASP: Top 10 2013-A3: Cross-Site Scripting (XSS)](https://www.owasp.org/index.php/Top_10_2013-A3-Cross-Site_Scripting_%28XSS%29)  
[CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')](http://cwe.mitre.org/data/definitions/79.html)

