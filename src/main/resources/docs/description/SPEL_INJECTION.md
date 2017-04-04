 A Spring expression is built with a dynamic value. The source of the value(s) should be verified to avoid that unfiltered values fall into this risky code evaluation.

**Code at risk:**

```
public void parseExpressionInterface(Person personObj,String property) {

        ExpressionParser parser = new SpelExpressionParser();

        //Unsafe if the input is control by the user..
        Expression exp = parser.parseExpression(property+" == 'Albert'");

        StandardEvaluationContext testContext = new StandardEvaluationContext(personObj);
        boolean result = exp.getValue(testContext, Boolean.class);
[...]
```
  

**References**  
[CWE-94: Improper Control of Generation of Code ('Code Injection')](http://cwe.mitre.org/data/definitions/94.html)  
[CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')](http://cwe.mitre.org/data/definitions/95.html)  
[Spring Expression Language (SpEL) - Official Documentation](http://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/expressions.html)  
[Minded Security: Expression Language Injection](https://www.mindedsecurity.com/fileshare/ExpressionLanguageInjection.pdf)  
[Remote Code Execution .. by design](http://blog.h3xstream.com/2014/11/remote-code-execution-by-design.html): Example of malicious payload. The samples given could be used to test sandboxing rules.

