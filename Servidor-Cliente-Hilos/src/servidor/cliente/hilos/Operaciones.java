package servidor.cliente.hilos;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Operaciones {

    public String cuentas(String operacion) {
        String resultado = resolverOperacion(operacion);
        return resultado;
    }

    private String resolverOperacion(String op) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(op);
            return result.toString();
        } catch (Exception e) {
            return "Error en operación.";
        }
    }

}
