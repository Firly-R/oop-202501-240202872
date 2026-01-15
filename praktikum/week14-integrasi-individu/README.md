# Week 14 - Integrasi Individu (OOP + Database + GUI)

Aplikasi **Agri-POS** yang mengintegrasikan konsep Object-Oriented Programming (OOP), Database (PostgreSQL), dan Graphical User Interface (JavaFX).

## 📋 Fitur Aplikasi

1. **Manajemen Produk**
   - Lihat daftar produk dari database
   - Tambah produk baru
   - Hapus produk
   - Validasi input dengan exception handling

2. **Keranjang Belanja**
   - Tambah item ke keranjang
   - Hapus item dari keranjang
   - Hitung total belanja otomatis
   - Checkout dengan konfirmasi

3. **Integrasi Database**
   - JDBC connection ke PostgreSQL
   - Operasi CRUD untuk produk
   - Prepared statements untuk keamanan

## 🏗️ Arsitektur

```
View (PosView.java)
    ↓
Controller (PosController.java)
    ↓
Service (ProductService, CartService)
    ↓
DAO (JdbcProductDAO)
    ↓
Database (PostgreSQL)
```

## 📁 Struktur Direktori

```
week14-integrasi-individu/
├── src/
│   ├── main/java/com/upb/agripos/
│   │   ├── AppJavaFX.java           - Main entry point
│   │   ├── model/                   - Data models
│   │   ├── dao/                     - Database access layer
│   │   ├── service/                 - Business logic
│   │   ├── controller/              - MVC controller
│   │   └── view/                    - JavaFX GUI
│   └── test/java/com/upb/agripos/
│       └── service/CartServiceTest.java
├── sql/
│   └── product.sql                  - Database schema
├── pom.xml                          - Maven configuration
├── laporan.md                       - Detailed report
└── README.md                        - This file
```

## 🔧 Teknologi

- **Language**: Java 17+
- **Framework**: JavaFX 17.0.6
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Testing**: JUnit 4
- **Patterns**: Singleton, MVC, DAO

## 📦 Dependencies

- javafx-controls
- javafx-fxml
- postgresql driver
- junit

## 🚀 Cara Menjalankan

### Prerequisites
- JDK 17+
- Maven 3.8+
- PostgreSQL (running di localhost:5432)
- Database `agripos` dengan tabel `products`

### Setup Database
```bash
# Connect ke PostgreSQL
psql -U postgres -d agripos

# Run schema
\i praktikum/week14-integrasi-individu/sql/product.sql
```

### Run Application
```bash
cd praktikum/week14-integrasi-individu

# Build
mvn clean compile

# Run tests
mvn test

# Run application
mvn javafx:run
```

## ✅ Test Results

9 JUnit test cases untuk CartService - **All Passed ✅**

```
✓ testAddToCart
✓ testAddMultipleItems
✓ testAddSameProductIncreaseQuantity
✓ testRemoveFromCart
✓ testClearCart
✓ testAddToCartWithZeroQuantity
✓ testAddToCartWithNegativeQuantity
✓ testAddToCartExceedsStock
✓ testGetCartItems
```

## 🎯 Design Patterns

1. **Singleton Pattern** - Cart class untuk single instance keranjang
2. **MVC Pattern** - Separation of View, Controller, Model
3. **DAO Pattern** - Data access abstraction layer
4. **Strategy Pattern** - Berbagai operasi bisnis di Service layer

## 📝 Laporan Lengkap

Lihat `laporan.md` untuk:
- Ringkasan aplikasi lengkap
- Traceability table
- UML diagrams
- Exception handling details
- Kendala dan solusi

## 👤 Author

**NIM**: 240202872

## 📅 Date

January 15, 2026
