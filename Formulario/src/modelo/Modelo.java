package modelo;

public class Modelo {

    //Nombre: ___________ (máximo 20 caracteres)
    public boolean NombreValido(String nombre) {

        if (nombre == null || nombre.length() > 20) {
            System.out.println("El nombre no es valido");
            return false;
        }
        return true;
    }

    //Apellido: ___________ (máximo 20 caracteres)
    public boolean AppelidoValido(String apellido) {

        if (apellido == null || apellido.length() > 20) {
            System.out.println("El apellido no es valido");
            return false;
        }
        return true;
    }

    //Dni: ___________ ( 8 dígitos numéricos, entre 10.000.000 y 60.000.000)
    public boolean DniValido(String dniTexto) {

        if (dniTexto == null || !dniTexto.matches("\\d{8}")) {
            System.out.println("DNI debe tener exactamente 8 dígitos numéricos.");
            return false;
        }

        try {
            int dni = Integer.parseInt(dniTexto);
            if (dni < 10000000 || dni > 60000000) {
                System.out.println("DNI fuera de rango permitido (10.000.000 a 60.000.000).");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("DNI inválido. No es un número.");
            return false;
        }

        return true;
    }

    //Pasaporte:__________ (1 dígito Alfabético A-Z y 8 numéricos entre 10.000.000 y
    //60.000.000) 
    public boolean pasaporteValido(String pasaporte) {

        if (pasaporte == null || !pasaporte.matches("[A-Z]\\d{8}")) {
            System.out.println("Formato inválido. Debe ser una letra seguida de 8 números.");
            return false;
        }

        int numero = Integer.parseInt(pasaporte.substring(1));

        if (numero < 10000000 || numero > 60000000) {
            System.out.println("Número del pasaporte fuera de rango permitido.");
            return false;
        }

        return true;
    }

    //Teléfono: ___________ ( > 6 dígitos numéricos y "+()-") ej:+54 9 (261)-5-012345
    public boolean telefonoValido(String telefonoText) {
        if (telefonoText == null || telefonoText.length() <= 6) {
            System.out.println("El teléfono es demasiado corto.");
            return false;
        }

        if (!telefonoText.matches("[\\d\\s+()\\-]+")) {
            System.out.println("El teléfono contiene caracteres inválidos.");
            return false;
        }

        return true;
    }
    //codigo postal solo tiene que tener 4 digitos
    public boolean codigoPostalValido(String codigoPostalTexto) {
        if (codigoPostalTexto == null || !codigoPostalTexto.matches("\\d{4}")) {
            System.out.println("Código postal inválido. Debe tener 4 dígitos numéricos.");
            return false;
        }

        int[] codigoArray = new int[4];
        for (int i = 0; i < 4; i++) {
            codigoArray[i] = Character.getNumericValue(codigoPostalTexto.charAt(i));
            if (codigoArray[i] < 0 || codigoArray[i] > 9) {
                System.out.println("Cada dígito debe estar entre 0 y 9.");
                return false;
            }
        }

        return true;
    }

    //Validación de DNI vs Pasaporte
    public boolean validarDocumento(String dni, String pasaporte) {
    boolean dniVacio = dni == null || dni.isEmpty();
    boolean pasaporteVacio = pasaporte == null || pasaporte.isEmpty();

    if (!dniVacio && !pasaporteVacio) {
        System.out.println("Solo uno entre DNI o Pasaporte debe completarse.");
        return false;
    }

    if (dniVacio && pasaporteVacio) {
        System.out.println("Debe completar DNI o Pasaporte.");
        return false;
    }

    if (!dniVacio) {
        return DniValido(dni);
    } else {
        return pasaporteValido(pasaporte);
    }
}

    //Domicilio: ______________________________ ( máximo 50 chr )
    public boolean domicilioValido(String domicilio) {
        if (domicilio == null || domicilio.length() > 50) {
            System.out.println("El domicilio es demasiado largo.");
            return false;
        }
        return true;
    }

}
