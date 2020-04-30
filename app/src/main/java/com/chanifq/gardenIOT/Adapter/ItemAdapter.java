package com.chanifq.gardenIOT.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanifq.gardenIOT.GrafikActivity;
import com.chanifq.gardenIOT.MainActivity;
import com.chanifq.gardenIOT.R;
import com.chanifq.gardenIOT.model.DataPlant;
import com.chanifq.gardenIOT.model.GlobalClass;
import com.chanifq.gardenIOT.model.ListPlant;
import com.google.android.gms.common.internal.GmsLogger;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final ArrayList<ListPlant> listItem = new ArrayList<ListPlant>();
    private Context context;
    private String tanggal,transaction_number;
    private static int enableEditJadwal;
    ItemViewHolder globalItemHolder;

    LocalBroadcastManager broadcaster;
    static boolean fsenin, fselasa, frabu, fkamis, fjumat, fsabtu, fminggu;

    public ItemAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        globalItemHolder = new ItemViewHolder(view);
        broadcaster = LocalBroadcastManager.getInstance(context);


        return globalItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.tvPlantName.setText(listItem.get(position).getName());
        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context.getApplicationContext(), GrafikActivity.class);
                GlobalClass globalClass = (GlobalClass) context.getApplicationContext();
                globalClass.setListPlants(listItem);
                globalClass.setModeNamePlant(listItem.get(position).getName());
                context.startActivity(i);

            }
        });
        String lastHumi="";
        String lastTemp="";
        String lastLight="";
        String lastHumiTanah="";
        for(DataPlant data: listItem.get(position).getPlants()){
            lastHumi=data.getHumidity();
            lastTemp=data.getTemperature();
            lastLight=data.getLight();
            lastHumiTanah=data.getHumidityTanah();
        }
        holder.tvLight.setText(lastLight);
        holder.tvHumi.setText(lastHumi);
        holder.tvTemp.setText(lastTemp);
        holder.tvTanah.setText(lastHumiTanah);

        holder.imgKipas.setVisibility(listItem.get(position).getDataSetting().getKipas().equals("1")?View.VISIBLE:View.GONE);
        holder.imgSemprot.setVisibility(listItem.get(position).getDataSetting().getSemprot().equals("1")?View.VISIBLE:View.GONE);
        holder.imgLampu.setVisibility(listItem.get(position).getDataSetting().getled().equals("1")?View.VISIBLE:View.GONE);
    }


    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public void setListItem(ArrayList<ListPlant> listItem) {
        this.listItem.clear();
        tanggal = ".";
        this.listItem.addAll(listItem);
        notifyDataSetChanged();
    }

    public ArrayList<ListPlant> getListItem() {
        return (listItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvStock, tvPlantName, tvFlow;
        CardView cvItem;
        TextView tvTemp,tvHumi,tvLight,tvTanah;
        ImageView imgSemprot,imgKipas,imgLampu;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItem= itemView.findViewById(R.id.cvItem);
            tvPlantName=itemView.findViewById(R.id.tvPlantName);
            tvTemp=itemView.findViewById(R.id.tvTemp);
            tvHumi=itemView.findViewById(R.id.tvHumi);
            tvLight=itemView.findViewById(R.id.tvLight);
            tvTanah=itemView.findViewById(R.id.tvHumiTanah);
            imgSemprot= itemView.findViewById(R.id.semprot);
            imgKipas= itemView.findViewById(R.id.kipas);
            imgLampu=itemView.findViewById(R.id.lampu);
        }
    }
}
