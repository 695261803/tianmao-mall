package com.taobao.mall.config;

import com.taobao.mall.entity.*;
import com.taobao.mall.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    private void initializeData() {
        // 检查是否已经初始化过数据
        if (categoryRepository.count() > 0) {
            return;
        }

        // 创建分类
        Category electronics = new Category();
        electronics.setName("电子产品");
        electronics.setDescription("各类电子设备");
        electronics.setIcon("electronics.png");
        electronics.setSortOrder(1);
        electronics = categoryRepository.save(electronics);

        Category clothing = new Category();
        clothing.setName("服装");
        clothing.setDescription("时尚服装");
        clothing.setIcon("clothing.png");
        clothing.setSortOrder(2);
        clothing = categoryRepository.save(clothing);

        Category books = new Category();
        books.setName("图书");
        books.setDescription("各类图书");
        books.setIcon("books.png");
        books.setSortOrder(3);
        books = categoryRepository.save(books);

        Category home = new Category();
        home.setName("家居用品");
        home.setDescription("家居生活用品");
        home.setIcon("home.png");
        home.setSortOrder(4);
        home = categoryRepository.save(home);

        // 创建子分类
        Category phones = new Category();
        phones.setName("手机");
        phones.setDescription("智能手机");
        phones.setIcon("phone.png");
        phones.setSortOrder(1);
        phones.setParent(electronics);
        phones = categoryRepository.save(phones);

        Category laptops = new Category();
        laptops.setName("电脑");
        laptops.setDescription("笔记本电脑");
        laptops.setIcon("laptop.png");
        laptops.setSortOrder(2);
        laptops.setParent(electronics);
        laptops = categoryRepository.save(laptops);

        // 创建用户
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@taobao.com");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setNickname("管理员");
        admin.setPhone("13800138000");
        admin.setGender(User.Gender.OTHER);
        admin.setStatus(User.UserStatus.ACTIVE);
        admin = userRepository.save(admin);

        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@taobao.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setNickname("测试用户");
        testUser.setPhone("13800138001");
        testUser.setGender(User.Gender.MALE);
        testUser.setStatus(User.UserStatus.ACTIVE);
        testUser = userRepository.save(testUser);

        // 创建商品
        Product iphone = new Product();
        iphone.setName("iPhone 15 Pro");
        iphone.setDescription("苹果最新旗舰手机，A17 Pro芯片，钛金属材质");
        iphone.setPrice(new BigDecimal("8999.00"));
        iphone.setOriginalPrice(new BigDecimal("9999.00"));
        iphone.setStock(100);
        iphone.setMainImage("iphone15pro.jpg");
        iphone.setCategory(phones);
        iphone.setStatus(Product.ProductStatus.ACTIVE);
        iphone.setSales(50);
        iphone.setViews(1000);
        iphone = productRepository.save(iphone);

        Product huawei = new Product();
        huawei.setName("华为Mate 60 Pro");
        huawei.setDescription("华为旗舰手机，麒麟9000s芯片");
        huawei.setPrice(new BigDecimal("6999.00"));
        huawei.setOriginalPrice(new BigDecimal("7999.00"));
        huawei.setStock(80);
        huawei.setMainImage("huaweiMate60.jpg");
        huawei.setCategory(phones);
        huawei.setStatus(Product.ProductStatus.ACTIVE);
        huawei.setSales(30);
        huawei.setViews(800);
        huawei = productRepository.save(huawei);

        Product macbook = new Product();
        macbook.setName("MacBook Pro 14");
        macbook.setDescription("苹果笔记本电脑，M3芯片");
        macbook.setPrice(new BigDecimal("14999.00"));
        macbook.setOriginalPrice(new BigDecimal("15999.00"));
        macbook.setStock(50);
        macbook.setMainImage("macbookpro14.jpg");
        macbook.setCategory(laptops);
        macbook.setStatus(Product.ProductStatus.ACTIVE);
        macbook.setSales(20);
        macbook.setViews(500);
        macbook = productRepository.save(macbook);

        Product javaBook = new Product();
        javaBook.setName("Java编程思想");
        javaBook.setDescription("经典的Java编程书籍");
        javaBook.setPrice(new BigDecimal("89.00"));
        javaBook.setOriginalPrice(new BigDecimal("99.00"));
        javaBook.setStock(200);
        javaBook.setMainImage("thinking_in_java.jpg");
        javaBook.setCategory(books);
        javaBook.setStatus(Product.ProductStatus.ACTIVE);
        javaBook.setSales(100);
        javaBook.setViews(2000);
        javaBook = productRepository.save(javaBook);

        // 创建地址
        Address address = new Address();
        address.setUser(testUser);
        address.setReceiverName("张三");
        address.setPhone("13800138001");
        address.setProvince("广东省");
        address.setCity("深圳市");
        address.setDistrict("南山区");
        address.setDetailAddress("科技园南区腾讯大厦");
        address.setPostalCode("518000");
        address.setIsDefault(true);
        address = addressRepository.save(address);

        // 创建购物车
        Cart cart = new Cart();
        cart.setUser(testUser);
        cart = cartRepository.save(cart);

        // 创建购物车商品
        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cart);
        cartItem1.setProduct(iphone);
        cartItem1.setQuantity(1);
        cartItemRepository.save(cartItem1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart);
        cartItem2.setProduct(javaBook);
        cartItem2.setQuantity(2);
        cartItemRepository.save(cartItem2);

        System.out.println("✅ 示例数据初始化完成！");
        System.out.println("🔐 管理员账号 - 用户名: admin, 密码: password");
        System.out.println("👤 测试用户账号 - 用户名: testuser, 密码: password");
        System.out.println("🗄️ H2数据库控制台: http://localhost:8080/api/h2-console");
        System.out.println("📊 JDBC URL: jdbc:h2:mem:taobao_mall");
        System.out.println("👤 数据库用户名: sa, 密码: 空");
    }
}