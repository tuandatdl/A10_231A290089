package vn.edu.vhu.ltdd.a10gallery;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {

    private static final String TAG = "A10_231A290089/Adapter";

    /** Giao diện callback để Activity xử lý sự kiện bấm — adapter không tự mở màn hình. */
    public interface OnAnhClickListener {
        void onAnhClick(int viTri);
    }

    static class AnhViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgAnh;
        final TextView tvChuThich;

        AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvChuThich = itemView.findViewById(R.id.tvChuThich);
        }
    }

    private final int[] anh;
    private final List<String> chuThich;
    private final OnAnhClickListener listener;

    public AnhAdapter(int[] anh, List<String> chuThich, OnAnhClickListener listener) {
        this.anh = anh;
        this.chuThich = chuThich;
        this.listener = listener;
    }

    /** Được gọi khi cần MỘT ô mới. Số lần gọi chỉ bằng số ô nhìn thấy, không phải bằng số ảnh. */
    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Tạo ViewHolder mới");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(v);
    }

    /** Được gọi mỗi khi một ô cần hiển thị dữ liệu của vị trí mới. */
    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        holder.imgAnh.setImageResource(anh[position]);
        holder.tvChuThich.setText(chuThich.get(position));

        // Dùng getBindingAdapterPosition(), KHÔNG dùng biến position,
        // vì vị trí có thể đã đổi khi người dùng bấm.
        holder.itemView.setOnClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) {
                listener.onAnhClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return anh.length;
    }
}
