 Concatenating unvalidated user input into a URL can allow an attacker to override the value of a request parameter. Attacker may be able to override existing parameter values, inject a new parameter or exploit variables out of a direct reach. HTTP Parameter Pollution (HPP) attacks consist of injecting encoded query string delimiters into other existing parameters. If a web application does not properly sanitize the user input, a malicious user may compromise the logic of the application to perform either client-side or server-side attacks.  
In the following example the programmer has not considered the possibility that an attacker could provide a lang such as en&user\_id=1, which would enable him to change the user\_id at will.

**Vulnerable Code:**

```
String lang = request.getParameter("lang");
GetMethod get = new GetMethod("http://www.host.com");
get.setQueryString("lang=" + lang + "&user_id=" + user_id);
get.execute();
```

**Solution:**  
Sanitize user input before using it in HTTP parameters.

  

**References**  
[CAPEC-460: HTTP Parameter Pollution (HPP)](https://capec.mitre.org/data/definitions/460.html)

 