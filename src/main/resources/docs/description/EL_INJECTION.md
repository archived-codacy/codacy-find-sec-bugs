 A expression is built with a dynamic value. The source of the value(s) should be verified to avoid that unfiltered values fall into this risky code evaluation.

**Code at risk:**

```
public void evaluateExpression(String expression) {
    FacesContext context = FacesContext.getCurrentInstance();
    ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
    ELContext elContext = context.getELContext();
    ValueExpression vex = expressionFactory.createValueExpression(elContext, expression, String.class);
    return (String) vex.getValue(elContext);
}
```
  

**References**  
[Minded Security: Abusing EL for executing OS commands](http://blog.mindedsecurity.com/2015/11/reliable-os-shell-with-el-expression.html)  
[The Java EE 6 Tutorial: Expression Language](https://docs.oracle.com/javaee/6/tutorial/doc/gjddd.html)  
[CWE-94: Improper Control of Generation of Code ('Code Injection')](http://cwe.mitre.org/data/definitions/94.html)  
[CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')](http://cwe.mitre.org/data/definitions/95.html)  
[Minded Security: Expression Language Injection](https://www.mindedsecurity.com/fileshare/ExpressionLanguageInjection.pdf)  
[Dan Amodio's blog: Remote Code with Expression Language Injection](http://danamodio.com/appsec/research/spring-remote-code-with-expression-language-injection/)  
[Remote Code Execution .. by design](http://blog.h3xstream.com/2014/11/remote-code-execution-by-design.html): Example of malicious payload. The samples given could be used to test sandboxing rules.

