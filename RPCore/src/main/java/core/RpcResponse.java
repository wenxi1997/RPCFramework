package core;

/**
 * @author jony
 */
public class RpcResponse {
    private String id;
    private Throwable error;
    private Object result;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
