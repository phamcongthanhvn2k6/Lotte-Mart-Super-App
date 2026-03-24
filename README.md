# Lotte Mart Super App 🛒

Dự án ứng dụng di động thương mại điện tử dành cho Lotte Mart, xây dựng trên nền tảng Android với kiến trúc **Multi-module Clean Architecture**, **MVVM**, và **Jetpack Compose**.

## 🏗 Cấu trúc thư mục (Folder Structure)

Dự án được chia thành các module hoàn toàn độc lập (Zero Merge Conflicts) để giúp cho việc làm việc nhóm (team work) diễn ra song song và mượt mà nhất.

```text
LotteMartSuperApp/
├── app/                            # Module chính (Entry point), chứa DI Graph tổng, App Navigation
├── build-logic/                    # Nơi chứa các Gradle Convention Plugins để tự động hoá script build
├── gradle/
│   └── libs.versions.toml          # Version Catalog: Nơi khai báo tất cả version thư viện (Dependencies)
│
├── core/                           # Các module nền tảng (chia sẻ API, code cấu hình cho các tính năng)
│   ├── common/                     # Utils, Extensions, Constants, DateFormatter
│   ├── network/                    # Retrofit config, Interceptors, xử lý HTTP Token & Error Handling
│   ├── database/                   # Room Database config, Local Caching
│   ├── datastore/                  # UserPreferences, Settings cấu hình máy
│   ├── model/                      # Các Business Object Model dùng chung toàn app (VD: User, Product, Error)
│   └── designsystem/               # Compose Theme (Màu Đỏ Lotte, Fonts), UI Components dùng chung
│
└── features/                       # Các mô-đun tính năng hoàn toàn độc lập 
    ├── auth/                       # Đăng nhập, Đăng ký, L.Point Auth
    ├── home/                       # Màn hình chính, Widget tìm kiếm (Elasticsearch)
    ├── cart/                       # Giỏ hàng, Tính toán khuyến mãi (Promotion Engine)
    ├── checkout/                   # Phương thức thanh toán, Gọi API VNPay / MoMo
    ├── employee/                   # Nhóm tính năng dành riêng cho nhân viên Lotte
    │   ├── barcode_scanner/        # Quét mã QR/Barcode kiểm giá (Google ML Kit)
    │   ├── shipper_tracking/       # Bản đồ lộ trình, chụp bằng chứng giao hàng POD (Google Maps)
    │   └── hr_manager/             # Chấm công GPS cho bộ phận nhân sự
    └── admin/                      # Dashboard BI & Thống kê doanh thu cho Quản lý
```

## 📐 Kiến trúc bên trong mỗi Feature Module (Clean Architecture)

Mọi thư mục tính năng (ví dụ `features/cart`) cũng tự nó tuân thủ nghiêm ngặt mô hình 3 lớp của **Clean Architecture**:

1. **`presentation/` (Giao diện):** 
   - Chứa code giao diện lập trình bằng **Jetpack Compose**.
   - Chứa **ViewModel** để quản lý trạng thái (`State`).
   - Lớp này *chỉ được phép* phụ thuộc (gọi code) của lớp `domain/`.
   
2. **`domain/` (Nghiệp vụ cốt lõi):**
   - Là trung tâm của ứng dụng. Chứa các logic tính toán (Ví dụ: `AddToCartUseCase`, `ApplyLPointPromoUseCase`).
   - Chứa các định nghĩa Interface của Repository.
   - Thư mục này chỉ xài *Pure Kotlin* (Mã nguồn thuần Kotlin), hoàn toàn không chứa bất cứ code dính dáng đến Android SDK.
   
3. **`data/` (Dữ liệu thực thi):**
   - Chứa các Class Implement của Interface Repository.
   - Data Source cho logic gọi Local Database (Room) và Remote API Server (Retrofit).
   - Có trách nhiệm Map (ánh xạ) dữ liệu DTO từ Server thành Domain Model.

## 🚀 Luồng làm việc chuẩn (Team Workflow)

👉 **1. Kỷ luật về Module:** Tuyệt đối không import trực tiếp code từ module feature này vào module feature khác (Ví dụ: module `cart` không được gọi code của `auth`). Nếu có biến/hàm cả 2 cùng cần, hãy tạo và vứt vào `core:common`.

👉 **2. Version Catalog:** Chỉ cập nhật hoặc thêm thư viện tại **1 nơi duy nhất** là file `gradle/libs.versions.toml`. Cấm copy-paste version số tay vào các file `build.gradle.kts`.

👉 **3. Branching GitHub:**
   - Hãy pull `master` mới nhất.
   - Tạo branch riêng biệt để làm tính năng cho bạn: `feature/tên-ngắn-gọn-tính-năng`.
   - Coding và Test chay OK -> Phải push lên và mở Pull Request (PR) để lead review. 

---
*Cùng nhau xây dựng Lotte Mart E-commerce chuẩn quốc tế!* 🚀
