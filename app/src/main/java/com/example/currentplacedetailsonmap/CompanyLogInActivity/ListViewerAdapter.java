package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.currentplacedetailsonmap.R;

import java.util.ArrayList;

public class ListViewerAdapter extends ArrayAdapter<ListItem> {

    public ListViewerAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }
        // Lookup view for data population
        TextView pName = (TextView) convertView.findViewById(R.id.pName);
        TextView pTypes = (TextView) convertView.findViewById(R.id.pTypes);
        TextView pCap = (TextView) convertView.findViewById(R.id.pCap);
        TextView pTot = (TextView) convertView.findViewById(R.id.pTot);
        ImageView pimage = (ImageView) convertView.findViewById(R.id.point_picture);
        // Populate the data into the template view using the data object
        pName.setText(item.point_name);
        pTypes.setText(item.getTypes());
        pCap.setText(String.valueOf(item.capcity));
        pTot.setText(String.valueOf(item.current));
        pimage.setImageDrawable(GetIconAndColor(getContext(),item.current,item.capcity,item.paper,item.plastic,item.glass));
        // Return the completed view to render on screen
        return convertView;
    }


    private Drawable GetIconAndColor(Context context , int size , int capacity , boolean ispaper , boolean isplastic , boolean isglass){
        double green = 50f/100f * capacity;
        double yellow = 70f/100f * capacity;
        double red = 95f/100f * capacity;
        if (ispaper && size <green && !isplastic && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_paper_blue, null);
        if (ispaper && size >=red && !isplastic && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_paper_red, null);
        if (ispaper && size >=yellow && !isplastic && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_paper_yellow, null);
        if (ispaper && size >=green && !isplastic && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_paper_green, null);

        if (isplastic && size <green && !ispaper && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_plastic_blue, null);
        if (isplastic && size >=red && !ispaper && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_plastic_red, null);
        if (isplastic && size >=yellow && !ispaper && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_plastic_yellow, null);
        if (isplastic && size >=green && !ispaper && !isglass)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_plastic_green, null);

        if (isglass && size <green && !ispaper && !isplastic)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_glass_blue, null);
        if (isglass && size >=red && !ispaper && !isplastic)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_glass_red, null);
        if (isglass && size >=yellow && !ispaper && !isplastic)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_glass_yellow, null);
        if (isglass && size >=green && !ispaper && !isplastic)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_glass_green, null);

        if (size < green)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_recycle, null);
        if (size >= red)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.red_recycle, null);
        if (size >= yellow)
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.yellow_recycle, null);


        return ResourcesCompat.getDrawable(context.getResources(), R.drawable.green_recycle, null);

    }

}
