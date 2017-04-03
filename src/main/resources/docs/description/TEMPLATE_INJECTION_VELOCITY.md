 Velocity template engine is powerful. It is possible to add logic including condition statements, loops and external calls. It is not design to be sandbox to templating operations. A malicious user in control of a template can run malicious code on the server-side. Velocity templates should be seen as scripts.

**Vulnerable Code:**

```
[...]

Velocity.evaluate(context, swOut, "test", userInput);
```

**Solution:**  
Avoid letting end users manipulate templates with Velocity. If you need to expose template editing to your users, prefer logic-less template engines such as Handlebars or Moustache (See references).

  

**References**  
[PortSwigger: Server-Side Template Injection](http://blog.portswigger.net/2015/08/server-side-template-injection.html)  
[Handlebars.java](https://jknack.github.io/handlebars.java/)

 