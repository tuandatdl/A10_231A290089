package vn.edu.vhu.ltdd.a10gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A10_231A290089";
    public static final String EXTRA_VI_TRI = "extra_vi_tri";

    /** Danh sách ảnh dạng List phục vụ tính năng xóa ảnh NC4 */
    public static final List<Integer> DANH_SACH_ANH = new ArrayList<>();

    static {
        DANH_SACH_ANH.add(R.drawable.anh1);
        DANH_SACH_ANH.add(R.drawable.anh2);
        DANH_SACH_ANH.add(R.drawable.anh3);
        DANH_SACH_ANH.add(R.drawable.anh4);
        DANH_SACH_ANH.add(R.drawable.anh5);
        DANH_SACH_ANH.add(R.drawable.anh6);
        DANH_SACH_ANH.add(R.drawable.anh7);
        DANH_SACH_ANH.add(R.drawable.anh8);
    }

    private final List<String> chuThich = new ArrayList<>();
    private AnhAdapter adapter;
    private RecyclerView rv;
    private boolean isStaggered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        chuThich.clear();
        for (int i = 0; i < DANH_SACH_ANH.size(); i++) {
            chuThich.add(getString(R.string.anh_so, i + 1));
        }

        rv = findViewById(R.id.rvAnh);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setHasFixedSize(true);

        adapter = new AnhAdapter(DANH_SACH_ANH, chuThich, new AnhAdapter.OnAnhClickListener() {
            @Override
            public void onAnhClick(int viTri) {
                Log.d(TAG, "Mở ảnh lớn tại vị trí " + viTri);
                Intent i = new Intent(MainActivity.this, ViewerActivity.class);
                i.putExtra(EXTRA_VI_TRI, viTri);
                startActivity(i);
            }

            @Override
            public void onAnhLongClick(int viTri) {
                // NC4: Hộp thoại xác nhận xóa ảnh dùng notifyItemRemoved
                xacNhanXoaAnh(viTri);
            }
        });
        rv.setAdapter(adapter);

        findViewById(R.id.btnDoiCot).setOnClickListener(v -> doiSoCot());

        // NC2: Chuyển đổi giữa GridLayoutManager và StaggeredGridLayoutManager
        findViewById(R.id.btnStaggered).setOnClickListener(v -> chuyenStaggered());
    }

    private void doiSoCot() {
        if (rv.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) rv.getLayoutManager();
            int soCot = lm.getSpanCount() + 1;
            if (soCot > 4) soCot = 2;
            lm.setSpanCount(soCot);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.so_cot, soCot), Toast.LENGTH_SHORT).show();
        } else {
            // Đang ở Staggered thì chuyển lại Grid 3 cột
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            isStaggered = false;
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Chuyển về Lưới chuẩn 3 cột", Toast.LENGTH_SHORT).show();
        }
    }

    private void chuyenStaggered() {
        if (!isStaggered) {
            // NC2: Lưới so le 2 cột dọc
            rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            isStaggered = true;
            Toast.makeText(this, "NC2: Bật Lưới so le Pinterest (2 cột)", Toast.LENGTH_SHORT).show();
        } else {
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            isStaggered = false;
            Toast.makeText(this, "Tắt Lưới so le -> Về Lưới chuẩn 3 cột", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void xacNhanXoaAnh(int viTri) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa ảnh?")
                .setMessage("Bạn có chắc muốn xóa " + chuThich.get(viTri) + " không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // NC4: Xóa dữ liệu và gọi notifyItemRemoved để kích hoạt hiệu ứng trượt
                    DANH_SACH_ANH.remove(viTri);
                    chuThich.remove(viTri);
                    adapter.notifyItemRemoved(viTri);
                    // Thông báo phạm vi sau đó cập nhật lại vị trí chính xác
                    adapter.notifyItemRangeChanged(viTri, DANH_SACH_ANH.size() - viTri);
                    Toast.makeText(this, "Đã xóa ảnh bằng notifyItemRemoved!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "NC4 - Đã xóa ảnh tại vị trí: " + viTri);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
