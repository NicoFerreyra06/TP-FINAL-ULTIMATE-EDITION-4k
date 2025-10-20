package Modelo;
import org.json.JSONObject;

public class Estadio {
    private String nombre;
    private int capacidad;
    private double valorEntrada;

    public Estadio(String nombre, int capacidad, double valorEntrada) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.valorEntrada = valorEntrada;
    }

    //==================== Constructores JSON ====================
    public Estadio (JSONObject json){
        this.nombre = json.getString("nombre");
        this.capacidad = json.getInt("capacidad");
        this.valorEntrada = json.getDouble("valorEntrada");
    }

    public JSONObject  toJSON(){
        JSONObject jsonEstadio = new JSONObject();
        jsonEstadio.put("nombre", nombre);
        jsonEstadio.put("capacidad", capacidad);
        jsonEstadio.put("valorEntrada", valorEntrada);
        return jsonEstadio;
    }
    // ==================== Getters y Setters ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double ingresoVisita) {
        this.valorEntrada = ingresoVisita;
    }

    /**
     * Calcula el ingreso segun la capacidad y las entradas vendidas.
     *
     * @return Ingreso total.
     */
    public double calcularIngreso(int entVendidas) {
        if (entVendidas > capacidad) return this.capacidad * valorEntrada;
        if (entVendidas < 0) return 0.0;
        return entVendidas * valorEntrada;
    }

    @Override
    public String toString() {
        return String.format("🏟️ Estadio: %s | Capacidad Máxima: %d personas | Entrada: $%.2f",
                this.nombre,
                this.capacidad,
                this.valorEntrada
        );
    }
}
