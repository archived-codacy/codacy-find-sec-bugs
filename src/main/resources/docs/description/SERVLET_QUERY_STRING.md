 The query string is the concatenation of the GET parameter names and values. Parameters other than those intended can be passed in.

For the URL request `/app/servlet.htm?a=1&b=2`, the query string extract will be `a=1&b=2`

Just as is true for individual parameter values retrieved via methods like `HttpServletRequest.getParameter()`, the value obtained from `HttpServletRequest.getQueryString()` should be considered unsafe. You may need to validate or sanitize anything pulled from the query string before passing it to sensitive APIs.

  

**Reference**  
[CWE-20: Improper Input Validation](http://cwe.mitre.org/data/definitions/20.html)

