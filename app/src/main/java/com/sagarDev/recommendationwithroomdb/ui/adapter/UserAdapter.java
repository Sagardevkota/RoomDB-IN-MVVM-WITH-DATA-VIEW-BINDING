package com.sagarDev.recommendationwithroomdb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagarDev.recommendationwithroomdb.R;
import com.sagarDev.recommendationwithroomdb.db.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> mUsers;
    private Context context;

  public UserAdapter(List<User> users,Context context){
        this.mUsers=users;
        this.context=context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.users_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (mUsers!=null)
        {
            final User current=mUsers.get(position);
            holder.tvFirst.setText(current.firstName);
            holder.tvLast.setText(current.lastName);
        }


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(List<User> users) {
        mUsers=users;
        notifyDataSetChanged();
    }

    public User getUserAt(int position)
    {
        return mUsers.get(position);
    }

    public void removeUser(int position)
    {
        mUsers.remove(position);
        notifyItemRemoved(position);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFirst,tvLast;

        private UserViewHolder(View itemView) {
            super(itemView);
            tvFirst = itemView.findViewById(R.id.tvFirstName);
            tvLast = itemView.findViewById(R.id.tvLastName);
        }
    }


}
