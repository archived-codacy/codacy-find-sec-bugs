 Form inputs should have minimal input validation. Preventive validation helps provide defense in depth against a variety of risks.

Validation can be introduce by implementing a `validate` method.

```
public class RegistrationForm extends ValidatorForm {

    private String name;
    private String email;

    [...]

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        //Validation code for name and email parameters passed in via the HttpRequest goes here
    }
}
```
  

**References**  
[CWE-20: Improper Input Validation](http://cwe.mitre.org/data/definitions/20.html)  
[CWE-106: Struts: Plug-in Framework not in Use](http://cwe.mitre.org/data/definitions/106.html)

