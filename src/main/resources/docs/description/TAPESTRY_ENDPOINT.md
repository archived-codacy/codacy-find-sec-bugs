 A Tapestry endpoint was discovered at application startup. Tapestry apps are structured with a backing Java class and a corresponding Tapestry Markup Language page (a .tml file) for each page. When a request is received, the GET/POST parameters are mapped to specific inputs in the backing Java class. The mapping is either done with fieldName:
```
[...]
    protected String input;
    [...]
```

or the definition of an explicit annotation:

```
[...]
    @org.apache.tapestry5.annotations.Parameter
    protected String parameter1;

    @org.apache.tapestry5.annotations.Component(id = "password")
    private PasswordField passwordField;
    [...]
```

The page is mapped to the view `[/resources/package/PageName].tml.`

Each Tapestry page in this application should be researched to make sure all inputs that are automatically mapped in this way are properly validated before they are used.

  

**References**  
[Apache Tapestry Home Page](http://tapestry.apache.org/)  
[CWE-20: Improper Input Validation](http://cwe.mitre.org/data/definitions/20.html)

