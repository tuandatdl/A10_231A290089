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

    public interface OnAnhClickListener {
        void onAnhClick(int viTri);
        void onAnhLongClick(int viTri); // NC4: Callback giữ lâu để xóa
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

    // NC4: Sử dụng List để có thể xóa và chèn phần tử linh hoạt
    private final List<Integer> anh;
    private final List<String> chuThich;
    private final OnAnhClickListener listener;

    public AnhAdapter(List<Integer> anh, List<String> chuThich, OnAnhClickListener listener) {
        this.anh = anh;
        this.chuThich = chuThich;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Tạo ViewHolder mới");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        holder.imgAnh.setImageResource(anh.get(position));
        holder.tvChuThich.setText(chuThich.get(position));

        // Chiều cao ngẫu nhiên giả lập cho NC2 (lưới so le phong cách Pinterest)
        ViewGroup.LayoutParams params = holder.imgAnh.getLayoutParams();
        params.height = (position % 2 == 0) ? 280 : 380;
        holder.imgAnh.setLayoutParams(params);

        holder.itemView.setOnClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) {
                listener.onAnhClick(p);
            }
        });

        // NC4: Giữ lâu vào ô ảnh để kích hoạt xóa
        holder.itemView.setOnLongClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) {
                listener.onAnhLongClick(p);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return anh.size();
    }
}
