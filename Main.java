import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // limpiar buffer
            throw new IllegalArgumentException("Debe ingresar un número válido");
        }
    }

    private static void crearProducto() {
        System.out.println("\n--- Crear Producto ---");
        System.out.print("Nombre: ");
        scanner.nextLine(); // limpiar buffer
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = leerDouble();

        System.out.print("Stock: ");
        int stock = leerInt();

        Producto producto = service.crear(nombre, precio, stock);
        System.out.println("Producto creado exitosamente: " + producto);
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        List<Producto> productos = service.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarProducto() {
        System.out.println("\n--- Buscar Producto ---");
        System.out.print("ID: ");
        int id = leerInt();

        Optional<Producto> optional = service.buscarPorId(id);
        if (optional.isPresent()) {
            System.out.println("Producto encontrado: " + optional.get());
        } else {
            System.out.println("No se encontró ningún producto con ID: " + id);
        }
    }

    private static void actualizarProducto() {
        System.out.println("\n--- Actualizar Producto ---");
        System.out.print("ID: ");
        int id = leerInt();

        Optional<Producto> optional = service.buscarPorId(id);
        if (optional.isEmpty()) {
            System.out.println("No se encontró ningún producto con ID: " + id);
            return;
        }

        System.out.println("Producto actual: " + optional.get());
        System.out.print("Nuevo nombre: ");
        scanner.nextLine(); // limpiar buffer
        String nombre = scanner.nextLine();

        System.out.print("Nuevo precio: ");
        double precio = leerDouble();

        System.out.print("Nuevo stock: ");
        int stock = leerInt();

        boolean actualizado = service.actualizar(id, nombre, precio, stock);
        if (actualizado) {
            System.out.println("Producto actualizado exitosamente.");
        } else {
            System.out.println("No se pudo actualizar el producto.");
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- Eliminar Producto ---");
        System.out.print("ID: ");
        int id = leerInt();

        boolean eliminado = service.eliminar(id);
        if (eliminado) {
            System.out.println("Producto eliminado exitosamente.");
        } else {
            System.out.println("No se encontró ningún producto con ID: " + id);
        }
    }

    private static double leerDouble() {
        try {
            double valor = scanner.nextDouble();
            scanner.nextLine(); // limpiar buffer
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // limpiar buffer
            throw new IllegalArgumentException("Debe ingresar un número decimal válido");
        }
    }

    private static int leerInt() {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // limpiar buffer
            throw new IllegalArgumentException("Debe ingresar un número entero válido");
        }
    }
}