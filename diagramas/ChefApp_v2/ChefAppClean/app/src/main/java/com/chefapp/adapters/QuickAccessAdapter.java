package com.chefapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chefapp.R;
import com.chefapp.models.QuickAccessSite;

import java.util.List;

public class QuickAccessAdapter extends RecyclerView.Adapter<QuickAccessAdapter.ViewHolder> {

    public interface OnSiteClickListener {
        void onSiteClick(QuickAccessSite site);
    }

    private final List<QuickAccessSite> sites;
    private OnSiteClickListener listener;

    public QuickAccessAdapter(List<QuickAccessSite> sites) {
        this.sites = sites;
    }

    public void setOnSiteClickListener(OnSiteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quick_access, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuickAccessSite site = sites.get(position);
        holder.tvName.setText(site.getName());
        holder.tvStatus.setText(site.isActive() ? "• Activo" : "• Inactivo");
        holder.tvStatus.setTextColor(holder.itemView.getContext().getResources()
                .getColor(site.isActive() ? R.color.imc_normal : R.color.text_secondary, null));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onSiteClick(site);
        });
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_site_name);
            tvStatus = itemView.findViewById(R.id.tv_site_status);
        }
    }
}
