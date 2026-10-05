import java.util.Scanner;

import model.Producto;
import service.ProductoService;
import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;
import util.Validador;

/**
 * Clase principal: punto de entrada de la aplicación.
 * Contiene el menú interactivo y maneja la interacción con el usuario.
 * Delega la lógica de negocio al ProductoService.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoService service = new ProductoService();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> crearProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 6 -> {
                        System.out.println("¡Hasta luego!");
                        salir = true;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error de validación: " + e.getMessage());
            } catch (ProductoNoEncontradoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (StockInsuficienteException e) {
                System.out.println("Error de stock: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("===== MENÚ DE PRODUCTOS =====");
        System.out.println("1. Crear producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // limpiar buffer
            throw new IllegalArgumentException("Debe ingresar un número válido");
        }
    }

    private static void crearProducto() {
        System.out.println("\n--- Crear Producto ---");
        String nombre = Validador.leerTexto(scanner, "Nombre:");
        double precio = Validador.leerDouble(scanner, "Precio:");
        int stock = Validador.leerEntero(scanner, "Stock:");
        String categoria = Validador.leerTexto(scanner, "Categoría:");

        Producto producto = new Producto(nombre, precio, stock, categoria);
        Producto guardado = service.guardar(producto);
        System.out.println("Producto creado exitosamente: " + guardado);
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        var productos = service.listarTodos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarProducto() {
        System.out.println("\n--- Buscar Producto ---");
        int id = Validador.leerEntero(scanner, "ID:");

        try {
            Producto producto = service.obtenerPorId(id);
            System.out.println("Producto encontrado: " + producto);
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void actualizarProducto() {
        System.out.println("\n--- Actualizar Producto ---");
        int id = Validador.leerEntero(scanner, "ID:");

        try {
            Producto actual = service.obtenerPorId(id);
            System.out.println("Producto actual: " + actual);

            String nombre = Validador.leerTexto(scanner, "Nuevo nombre:");
            double precio = Validador.leerDouble(scanner, "Nuevo precio:");
            int stock = Validador.leerEntero(scanner, "Nuevo stock:");
            String categoria = Validador.leerTexto(scanner, "Nueva categoría:");

            Producto datos = new Producto(nombre, precio, stock, categoria);
            Producto actualizado = service.actualizar(id, datos);
            System.out.println("Producto actualizado exitosamente: " + actualizado);
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- Eliminar Producto ---");
        int id = Validador.leerEntero(scanner, "ID:");

        try {
            service.eliminar(id);
            System.out.println("Producto eliminado exitosamente.");
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}