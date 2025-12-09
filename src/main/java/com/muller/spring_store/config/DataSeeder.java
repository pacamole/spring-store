package com.muller.spring_store.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.muller.spring_store.model.Order;
import com.muller.spring_store.model.OrderItem;
import com.muller.spring_store.model.OrderStatus;
import com.muller.spring_store.model.Product;
import com.muller.spring_store.model.User;
import com.muller.spring_store.model.UserRole;
import com.muller.spring_store.repository.OrderRepository;
import com.muller.spring_store.repository.ProductRepository;
import com.muller.spring_store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem dados para não duplicar
        if (userRepository.count() == 0) {
            seedUsers();
        }

        if (productRepository.count() == 0) {
            seedProducts();
        }

        if (orderRepository.count() == 0) {
            seedOrders();
        }
    }

    private void seedUsers() {
        String encodedPwd = passwordEncoder.encode("1234");

        // Criando usuários manualmente para evitar erros de construtor
        User admin = new User();
        admin.setName("Admin Chefe");
        admin.setEmail("admin@store.com");
        admin.setPassword(encodedPwd);
        admin.setRole(UserRole.ADMIN);

        User client = new User();
        client.setName("Cliente Feliz");
        client.setEmail("cliente@store.com");
        client.setPassword(encodedPwd);
        client.setRole(UserRole.USER);

        // Criando lista de usuários extras
        List<User> extraUsers = new ArrayList<>();
        String[] names = {"João Silva", "Maria Souza", "Pedro Santos", "Ana Costa", "Carlos Oliveira", "Fernanda Lima", "Lucas Pereira", "Juliana Martins"};
        
        for (String name : names) {
            User u = new User();
            u.setName(name);
            u.setEmail(name.toLowerCase().replace(" ", ".") + "@email.com");
            u.setPassword(encodedPwd);
            u.setRole(UserRole.USER);
            extraUsers.add(u);
        }

        List<User> allUsers = new ArrayList<>();
        allUsers.add(admin);
        allUsers.add(client);
        allUsers.addAll(extraUsers);

        userRepository.saveAll(allUsers);
        System.out.println("✅ Usuários criados com sucesso!");
    }

    private void seedProducts() {
        List<Product> products = new ArrayList<>();

        // Helper interno para criar produto rápido (já que você não quer método auxiliar fora)
        // Mas usar setters aqui garante que não teremos erro de "Constructor undefined"
        products.add(makeProduct("Notebook Gamer Alienware", "Intel i9, RTX 4080", "15000.00", 10));
        products.add(makeProduct("Mouse Logitech MX", "Sem fio, ergonômico", "350.00", 50));
        products.add(makeProduct("Teclado Mecânico Keychron", "Switch Brown, RGB", "800.00", 30));
        products.add(makeProduct("Monitor Dell UltraSharp", "4K, USB-C", "3200.00", 15));
        products.add(makeProduct("Headset Sony XM5", "Cancelamento de ruído", "1800.00", 20));
        products.add(makeProduct("Cadeira Gamer DX", "Conforto máximo", "1200.00", 5));
        products.add(makeProduct("Webcam Logitech 4K", "Ideal para streaming", "900.00", 25));
        products.add(makeProduct("Microfone HyperX", "Qualidade de estúdio", "750.00", 40));
        products.add(makeProduct("SSD NVMe 2TB", "Leitura 7000MB/s", "1100.00", 60));
        products.add(makeProduct("Placa de Vídeo RTX 4090", "24GB VRAM", "12000.00", 3));

        productRepository.saveAll(products);
        System.out.println("✅ Produtos criados com sucesso!");
    }

    private Product makeProduct(String name, String desc, String price, Integer stock) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(new BigDecimal(price));
        p.setStockQuantity(stock);
        return p;
    }

    private void seedOrders() {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        if (users.isEmpty() || products.isEmpty()) return;

        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = users.get(i % users.size());
            
            Order order = new Order();
            order.setUser(user);
            order.setCreatedAt(LocalDateTime.now().minusDays(i));
            order.setStatus(OrderStatus.PAID);
            order.setItems(new ArrayList<>()); // Inicializa lista vazia

            // Item 1
            Product p1 = products.get(i % products.size());
            OrderItem item1 = new OrderItem();
            item1.setProduct(p1);
            item1.setOrder(order);
            item1.setQuantity(1);
            item1.setUnitPrice(p1.getPrice());
            
            // Item 2
            Product p2 = products.get((i + 1) % products.size());
            OrderItem item2 = new OrderItem();
            item2.setProduct(p2);
            item2.setOrder(order);
            item2.setQuantity(2);
            item2.setUnitPrice(p2.getPrice());

            order.getItems().add(item1);
            order.getItems().add(item2);

            // Calcula total
            BigDecimal total = item1.getUnitPrice().multiply(new BigDecimal(item1.getQuantity()))
                    .add(item2.getUnitPrice().multiply(new BigDecimal(item2.getQuantity())));
            order.setTotal(total);

            orders.add(order);
        }

        orderRepository.saveAll(orders);
        System.out.println("✅ Pedidos criados com sucesso!");
    }
}