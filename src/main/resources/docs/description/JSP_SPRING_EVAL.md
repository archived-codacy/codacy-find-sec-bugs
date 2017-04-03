 A Spring expression is built with a dynamic value. The source of the value(s) should be verified to avoid that unfiltered values fall into this risky code evaluation.

**Vulnerable Code:**

```
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:eval expression="${param.lang}" var="lang" />
```
  

```
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:eval expression="'${param.lang}'=='fr'" var="languageIsFrench" />
```

**Solution:**

```
<c:set var="lang" value="${param.lang}"/>
```
  

```
<c:set var="languageIsFrench" value="${param.lang == 'fr'}"/>
```
  

**References**  
[CWE-94: Improper Control of Generation of Code ('Code Injection')](http://cwe.mitre.org/data/definitions/94.html)  
[CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')](http://cwe.mitre.org/data/definitions/95.html)

 