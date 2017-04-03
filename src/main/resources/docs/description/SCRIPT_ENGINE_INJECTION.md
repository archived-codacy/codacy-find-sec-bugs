 Dymanic code is being evaluate. A careful analysis of the code construction should be made. Malicious code execution could lead to data leakage or operating system compromised.

If the evaluation of user code is intended, a proper sandboxing should be applied (see references).

**Code at risk:**

```
public void runCustomTrigger(String script) {
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");

    engine.eval(script); //Bad things can happen here.
}
```

**Solution:**

Safe evaluation of Javascript code using "Cloudbees Rhino Sandbox" library.

```
public void runCustomTrigger(String script) {
    SandboxContextFactory contextFactory = new SandboxContextFactory();
    Context context = contextFactory.makeContext();
    contextFactory.enterContext(context);
    try {
        ScriptableObject prototype = context.initStandardObjects();
        prototype.setParentScope(null);
        Scriptable scope = context.newObject(prototype);
        scope.setPrototype(prototype);

        context.evaluateString(scope,script, null, -1, null);
    } finally {
        context.exit();
    }
}
```
  

**References**  
[Cloudbees Rhino Sandbox](https://github.com/cloudbees/rhino-sandbox): Utility to create sandbox with Rhino (block access to all classes)  
[CodeUtopia.net: Sandboxing Rhino in Java](http://codeutopia.net/blog/2009/01/02/sandboxing-rhino-in-java/)  
[Remote Code Execution .. by design](http://blog.h3xstream.com/2014/11/remote-code-execution-by-design.html): Example of malicious payload. The samples given could be used to test sandboxing rules.  
[CWE-94: Improper Control of Generation of Code ('Code Injection')](http://cwe.mitre.org/data/definitions/94.html)  
[CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')](https://cwe.mitre.org/data/definitions/95.html)

