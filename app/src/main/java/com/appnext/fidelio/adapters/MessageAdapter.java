package com.appnext.fidelio.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnext.fidelio.utils.ParseConstants;
import com.example.manfredi.chattit.R;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by manfredi on 20/06/15.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    public static final String TAG = MessageAdapter.class.getSimpleName();

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);
        mContext = context;
        mMessages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.timeLabel = (TextView)convertView.findViewById(R.id.timeLabel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        ParseObject message = mMessages.get(position);

        Date createdAt = message.getCreatedAt();
        long now = new Date().getTime();
        String convertedDate = DateUtils.getRelativeTimeSpanString(createdAt.getTime(), now, DateUtils.SECOND_IN_MILLIS).toString();

        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.drawable.ic_picture);
        }
        else {
            holder.iconImageView.setImageResource(R.drawable.ic_video);
        }

        //holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
        holder.timeLabel.setText(convertedDate);

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView timeLabel;
    }

    public void refill(List<ParseObject> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }
}
