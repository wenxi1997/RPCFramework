package core;

/**
 * @author jony
 */
public class RpcRequest {
    private String id;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getId() {
        return id;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
