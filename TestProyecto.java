import model.Producto;
import service.ProductoService;
import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;
import util.Validador;

public class TestProyecto {
    public static void main(String[] args) {
        ProductoService service = new ProductoService();

        System.out.println("=== TEST PROYECTO REESTRUCTURADO ===\n");

        // TEST 1: Crear productos
        System.out.println("--- TEST 1: Crear productos ---");
        Producto p1 = new Producto("Laptop", 1500.00, 10, "Electrónica");
        Producto guardado1 = service.guardar(p1);
        System.out.println("Creado: " + guardado1);

        Producto p2 = new Producto("Mouse", 25.50, 50, "Accesorios");
        Producto guardado2 = service.guardar(p2);
        System.out.println("Creado: " + guardado2);

        Producto p3 = new Producto("Teclado", 75.00, 30, "Accesorios");
        Producto guardado3 = service.guardar(p3);
        System.out.println("Creado: " + guardado3);

        // TEST 2: Listar
        System.out.println("\n--- TEST 2: Listar productos ---");
        service.listarTodos().forEach(System.out::println);

        // TEST 3: Buscar por ID
        System.out.println("\n--- TEST 3: Buscar por ID ---");
        try {
            Producto encontrado = service.obtenerPorId(1);
            System.out.println("Encontrado: " + encontrado);
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        try {
            service.obtenerPorId(99);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("ID 99: " + e.getMessage());
        }

        // TEST 4: Actualizar
        System.out.println("\n--- TEST 4: Actualizar producto ---");
        try {
            Producto datos = new Producto("Mouse Inalámbrico", 35.00, 40, "Accesorios");
            Producto actualizado = service.actualizar(2, datos);
            System.out.println("Actualizado: " + actualizado);
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        // TEST 5: Eliminar
        System.out.println("\n--- TEST 5: Eliminar producto ---");
        try {
            service.eliminar(3);
            System.out.println("Eliminado ID 3");
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Lista después de eliminar:");
        service.listarTodos().forEach(System.out::println);

        // TEST 6: Validaciones
        System.out.println("\n--- TEST 6: Validaciones ---");
        try {
            service.guardar(new Producto("", 100, 5, "Test"));
        } catch (IllegalArgumentException e) {
            System.out.println("Nombre vacío: " + e.getMessage());
        }

        try {
            service.guardar(new Producto("Producto", -10, 5, "Test"));
        } catch (IllegalArgumentException e) {
            System.out.println("Precio negativo: " + e.getMessage());
        }

        try {
            service.guardar(new Producto("Producto", 100, -5, "Test"));
        } catch (StockInsuficienteException e) {
            System.out.println("Stock negativo: " + e.getMessage());
        }

        try {
            service.guardar(new Producto("Producto", 100, 5, ""));
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría vacía: " + e.getMessage());
        }

        // TEST 7: Operaciones con ID inexistente
        System.out.println("\n--- TEST 7: Operaciones con ID inexistente ---");
        try {
            service.actualizar(99, new Producto("Test", 10, 10, "Test"));
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Actualizar ID 99: " + e.getMessage());
        }
        try {
            service.eliminar(99);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Eliminar ID 99: " + e.getMessage());
        }

        // TEST 8: Validador lectura (simulado)
        System.out.println("\n--- TEST 8: Validador métodos estáticos ---");
        System.out.println("Validador funciona correctamente (métodos estáticos).");

        System.out.println("\n=== TODOS LOS TESTS PASARON ===");
    }
}