 An attacker can set arbitrary bean properties that can compromise system integrity. Bean population functions allow to set a bean property or a nested property. An attacker can leverage this functionality to access special bean properties like class.classLoader that will allow him to override system properties and potentially execute arbitrary code.

**Vulnerable Code:**

```
MyBean bean = ...;
HashMap map = new HashMap();
Enumeration names = request.getParameterNames();
while (names.hasMoreElements()) {
    String name = (String) names.nextElement();
    map.put(name, request.getParameterValues(name));
}
BeanUtils.populate(bean, map);
```

**Solution:**  
Avoid using user controlled values to populate Bean property names.

  

**References**  
[CWE-15: External Control of System or Configuration Setting](https://cwe.mitre.org/data/definitions/15.html)

 