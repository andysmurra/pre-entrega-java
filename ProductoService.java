import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoService {
    private List<Producto> productos;
    private int nextId;

    public ProductoService() {
        this.productos = new ArrayList<>();
        this.nextId = 1;
    }

    public Producto crear(String nombre, double precio, int stock) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        Producto producto = new Producto(nextId++, nombre, precio, stock);
        productos.add(producto);
        return producto;
    }

    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public Optional<Producto> buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public boolean actualizar(int id, String nombre, double precio, int stock) {
        Optional<Producto> optionalProducto = buscarPorId(id);
        if (optionalProducto.isEmpty()) {
            return false;
        }

        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        Producto producto = optionalProducto.get();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        return true;
    }

    public boolean eliminar(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    private void validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    private void validarStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}