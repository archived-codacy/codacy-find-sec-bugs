 Prior to HTML5, Web browsers enforced the Same Origin Policy which ensures that in order for JavaScript to access the contents of a Web page, both the JavaScript and the Web page must originate from the same domain. Without the Same Origin Policy, a malicious website could serve up JavaScript that loads sensitive information from other websites using a client's credentials, cull through it, and communicate it back to the attacker. HTML5 makes it possible for JavaScript to access data across domains if a new HTTP header called Access-Control-Allow-Origin is defined. With this header, a Web server defines which other domains are allowed to access its domain using cross-origin requests. However, caution should be taken when defining the header because an overly permissive CORS policy will allow a malicious application to communicate with the victim application in an inappropriate way, leading to spoofing, data theft, relay and other attacks.

**Vulnerable Code:**

```
response.addHeader("Access-Control-Allow-Origin", "*");
```

**Solution:**  
Avoid using \* as the value of the Access-Control-Allow-Origin header, which indicates that the application's data is accessible to JavaScript running on any domain.

  

**References**  
[W3C Cross-Origin Resource Sharing](https://www.w3.org/TR/cors/)  
[Enable Cross-Origin Resource Sharing](http://enable-cors.org/)

