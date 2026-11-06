# INT4211 - LẬP TRÌNH TRÊN CÁC THIẾT BỊ DI ĐỘNG
## BÁO CÁO THỰC HÀNH LAB A10: GRIDVIEW, RECYCLERVIEW & VIEWPAGER2 (ALBUM ẢNH)

---

### THÔNG TIN SINH VIÊN
* **Họ và tên:** Đỗ Lê Tuấn Đạt
* **Mã số sinh viên (MSSV):** 231A290089
* **Lớp học phần:** Lập trình thiết bị di động (INT4211)
* **Môi trường thực thi:** Android Studio trên macOS (Apple Silicon)
* **Thiết bị thử nghiệm:** Máy ảo Pixel 8 (Android 17, API 37.1 – aarch64)


---

### CÁC MỤC ĐÃ HOÀN THÀNH

#### 1. Kiến trúc RecyclerView 3 thành phần & GridLayoutManager (Phần 1, 2, 3)
* Phân tách rõ ràng trách nhiệm của 3 thành phần:
  * **Adapter (`AnhAdapter`):** Cài đặt đủ 3 phương thức bắt buộc `onCreateViewHolder`, `onBindViewHolder`, `getItemCount`.
  * **ViewHolder (`AnhViewHolder`):** Lưu trữ sẵn con trỏ View `imgAnh` và `tvChuThich`, ngăn chặn triệt để việc gọi lại `findViewById`.
  * **LayoutManager (`GridLayoutManager`):** Thiết lập lưới 3 cột mặc định, đổi số cột linh hoạt (2, 3, 4 cột) lúc runtime bằng phương thức `setSpanCount()` mà không cần sửa đổi bất kỳ dòng mã nào trong Adapter.
* Thiết kế ô lưới `item_anh.xml` với `MaterialCardView` bo góc 10dp, cố định chiều cao ảnh `110dp` kết hợp `scaleType="centerCrop"` giúp lưới luôn vuông vắn, không bị méo tỉ lệ ảnh gốc.

#### 2. Xử lý Callback đúng chuẩn & An toàn vị trí (Phần 3.3)
* Khai báo interface callback `OnAnhClickListener` trong Adapter để Activity xử lý mở màn hình chuyển trang — tuân thủ nguyên tắc "Adapter chỉ hiển thị, Activity điều hướng".
* Sử dụng `holder.getBindingAdapterPosition()` và kiểm tra điều kiện `p != RecyclerView.NO_POSITION` (−1) thay cho biến `position`, tránh hoàn toàn lỗi crash khi người dùng thao tác bấm vào ô đang bị xóa hoặc di chuyển.

#### 3. Màn hình xem ảnh lớn ViewPager2 vuốt ngang (Phần 4)
* Tận dụng bản chất RecyclerView nằm ngang của `ViewPager2` để sử dụng trực tiếp `AnhPagerAdapter`.
* Mở đúng vị trí ảnh được chọn với `pager.setCurrentItem(viTri, false)` (tắt hiệu ứng trượt khi vừa mở) và cập nhật chỉ số `"X / 8"` mượt mà qua `registerOnPageChangeCallback()`.

#### 4. Bài nâng cao NC2: Lưới so le Pinterest với StaggeredGridLayoutManager
* Tích hợp nút chuyển đổi giao diện sang `StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)`, kết hợp gán chiều cao ngẫu nhiên (280dp / 380dp) tạo giao diện lưới ảnh so le hiện đại.

#### 5. Bài nâng cao NC4: Xóa ảnh mượt mà bằng `notifyItemRemoved`
* Bắt sự kiện giữ lâu (`onLongClick`) mở hộp thoại xác nhận xóa ảnh, xóa khỏi `List<Integer>` và gọi `notifyItemRemoved(viTri)` kết hợp `notifyItemRangeChanged`, kích hoạt hiệu ứng co trượt hoạt họa tự nhiên có sẵn của RecyclerView.

---

### KẾT QUẢ NGHIỆM THU 2 CHECKPOINT

1. **Nghiệm thu Checkpoint 1 (Phần 3 - Màn hình Lưới):**
   * Mở app thấy 8 ảnh hiển thị thành lưới 3 cột đều đặn với bo góc `MaterialCardView` 10dp và elevation 2dp.
   * Bấm nút "Đổi số cột" ➔ Lưới lần lượt chuyển thành 4 cột ➔ 2 cột ➔ 3 cột mượt mà, kèm Toast thông báo số cột hiện tại.
2. **Nghiệm thu Checkpoint 2 (Phần 4 - Màn hình Xem ảnh lớn):**
   * Tại lưới ảnh, chạm vào ảnh thứ 5 ➔ Màn hình `ViewerActivity` mở đúng ảnh thứ 5 ngay lập tức và bộ đếm phía dưới hiển thị `5 / 8`.
   * Vuốt sang trái ➔ ảnh chuyển sang ảnh 6 mượt mà và bộ đếm nhảy thành `6 / 8`.

---

### BẢNG KIỂM THỬ 7 KỊCH BẢN THỰC TẾ (PHẦN 5)

| STT | Thao tác thực nghiệm | Kết quả đúng phải thấy | Kết quả quan sát thực tế | Đạt? |
|:---:|:---|:---|:---|:---:|
| 1 | Mở app lần đầu | 8 ảnh hiện thành lưới 3 cột, ô đều nhau, ảnh không méo | Hiện đủ 8 ảnh card bo góc, ảnh crop đẹp mắt | **Đạt** |
| 2 | Cuộn lên xuống nhiều lần | Mượt; `onCreateViewHolder` chỉ chạy khoảng 9–12 lần | Cuộn cực mượt, Logcat dừng tạo ViewHolder sau khi đầy màn hình | **Đạt** |
| 3 | Bấm nút "Đổi số cột" | Lưới đổi 2 ➔ 3 ➔ 4 cột, adapter không đổi dòng code nào | Lưới co dãn số cột tức thì, có Toast thông báo số cột | **Đạt** |
| 4 | Chạm vào ảnh thứ 5 trong lưới | Màn hình xem lớn mở đúng ảnh 5, bộ đếm hiện `5 / 8` | Mở `ViewerActivity` đúng ảnh 5, bộ đếm hiện `5 / 8` | **Đạt** |
| 5 | Vuốt sang trái vài lần | Ảnh đổi theo, bộ đếm cập nhật đúng | Vuốt nhạy, chỉ số nhảy lần lượt `6 / 8`, `7 / 8` | **Đạt** |
| 6 | Vuốt tới ảnh cuối rồi vuốt tiếp | Dừng lại ở ảnh 8, không quay vòng, không crash | Có hiệu ứng nảy mép màn hình, giữ nguyên ở ảnh `8 / 8` | **Đạt** |
| 7 | Xoay ngang màn hình xem ảnh | Vẫn đúng ảnh đang xem; ảnh hiển thị vừa khung | `ViewPager2` tự phục hồi vị trí, ảnh `fitCenter` vừa vặn | **Đạt** |

---

### TRẢ LỜI 3 CÂU HỎI PHÂN TÍCH (PHẦN 5)

* **Câu 1: Ở kịch bản 2, thêm Log.d vào `onCreateViewHolder` rồi đếm. Vì sao con số nhỏ hơn 8 ảnh rất nhiều khi lưới 4 cột, nhưng lớn hơn khi lưới 2 cột?**
  * *Trả lời:* `onCreateViewHolder` chỉ được gọi để tạo ra số lượng ViewHolder vừa đủ lấp đầy khung nhìn hiển thị của màn hình cộng thêm một vài ô dự phòng (scrap views) để chuẩn bị cho việc cuộn.
    * Khi lưới 4 cột: kích thước mỗi ô bị thu nhỏ lại, nhưng màn hình hiển thị được nhiều hàng cùng lúc (ví dụ hiển thị cả 8 ảnh cùng một lúc trên màn hình), tổng số ô cần tạo ban đầu chính là 8 và hệ thống không cần tạo thêm ô mới vì không còn ảnh thừa để cuộn.
    * Khi lưới 2 cột: các ô có kích thước lớn hơn, màn hình máy ảo chỉ hiển thị được khoảng 4 ô (2 hàng). Khi người dùng cuộn xuống dưới rồi cuộn lên nhanh, để đảm bảo độ mượt mà không bị giật khung hình, RecyclerView sẽ tạo thêm các ViewHolder đệm (khoảng 8 đến 10 ô) trước khi cơ chế tái sử dụng (recycling) bắt đầu phát huy tác dụng hoàn toàn.

* **Câu 2: Ở kịch bản 3, bạn phải sửa những dòng code nào để đổi số cột? Nếu dùng GridView thì có làm được không? Vì sao?**
  * *Trả lời:* Trong RecyclerView, ta chỉ cần gọi duy nhất một dòng lệnh: `lm.setSpanCount(soCot);` kết hợp `adapter.notifyDataSetChanged();` — Adapter và ViewHolder giữ nguyên 100% không đổi một dòng code nào. Nếu dùng `GridView` đời cũ, thuộc tính số cột thường bị gán cứng trong tệp XML (`android:numColumns="3"`), việc thay đổi động lúc runtime đòi hỏi phải can thiệp phức tạp vào View cha hoặc phải khởi tạo lại toàn bộ Adapter từ đầu.

* **Câu 3: Ở kịch bản 7, vì sao ViewPager2 vẫn nhớ đúng trang đang xem sau khi Activity bị tạo lại?**
  * *Trả lời:* Bản thân widget ViewPager2 đã được gán thuộc tính `android:id="@+id/pager"` trong layout XML. Cơ chế nội tại của ViewPager2 đã được Google cài đặt sẵn việc ghi nhớ và tự động đóng gói thuộc tính `currentItem` vào `onSaveInstanceState` (Bundle) của hệ thống khi Activity bị hủy do xoay màn hình. Khi Activity được tạo lại (recreated), ViewPager2 sẽ tự động đọc lại Bundle này và khôi phục chính xác trang mà người dùng đang xem dở.
