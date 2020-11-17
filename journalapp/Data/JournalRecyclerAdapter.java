package edu.fdu.journalapp.Data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import edu.fdu.journalapp.Activities.DetailsActivity;
import edu.fdu.journalapp.Model.Journal;
import edu.fdu.journalapp.R;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Journal> journalList;

    public JournalRecyclerAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Journal journal = journalList.get(position);

        holder.affirmations.setText(journal.getAffirmations());

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(journal.getTimestamp())).getTime());

        holder.timestamp.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView affirmations;
        public TextView timestamp;
        String userID;

        public ViewHolder(@NonNull View view, Context ctx) { //Need context here to move from one view to another
            super(view);
            context = ctx;

            affirmations = (TextView) view.findViewById(R.id.dailyAffirmationsList);
            timestamp = (TextView) view.findViewById(R.id.timestampList);
            userID = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Go to next activity here
                    int position = getAdapterPosition();

                    Journal journal = journalList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("grateful1", journal.getGrateful1());
                    intent.putExtra("grateful2", journal.getGrateful2());
                    intent.putExtra("grateful3", journal.getGrateful3());
                    intent.putExtra("affirmations", journal.getAffirmations());
                    intent.putExtra("date", journal.getTimestamp());

                    Log.d("zz", String.valueOf(journal.getGrateful1()));
                    context.startActivity(intent);

                }
            });
        }
    }
}


