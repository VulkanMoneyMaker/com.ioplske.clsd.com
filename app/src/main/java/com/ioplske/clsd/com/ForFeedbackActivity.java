package com.ioplske.clsd.com;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.ioplske.clsd.com.models.Comment;
import com.ioplske.clsd.com.models.Spin;

import java.util.ArrayList;
import java.util.UUID;

import erty.maamoo.chatui.ChatView;
import erty.maamoo.chatui.models.ChatMessage;


public class ForFeedbackActivity extends AppCompatActivity {
    private static final String TAG = ForFeedbackActivity.class.getSimpleName();

    private ArrayList<Spin> spins = new ArrayList<>();
    private ArrayList<Comment> commentsTexts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_feedback);

        ArrayList<ChatMessage> list = new ArrayList<>();

        String[] array = getResources().getStringArray(R.array.users);
        String[] comments = getResources().getStringArray(R.array.comments);

        for (int i = 0; i < array.length; ++i) {

            Spin spin = Spin.getBuilder(i)
                    .setDate(System.currentTimeMillis())
                    .setTitle("Item - " + i)
                    .setUiid(UUID.randomUUID().toString()).build();
            spins.add(spin);

            commentsTexts.add(new Comment(i, comments[i]));

            ChatMessage message = new ChatMessage(comments[i],
                    array[i],
                    System.currentTimeMillis(),
                    ChatMessage.Type.RECEIVED);
            message.setTimestamp(System.currentTimeMillis());
            list.add(message);
        }

        ChatView view = findViewById(R.id.chat_view);
        view.addMessages(list);
        view.setOnSentMessageListener(chatMessage -> {
            Spin spin = spins.get(0);
            chatMessage.setUserName(commentsTexts.size() + "text");
            chatMessage.setUserName(getString(R.string.you));
            chatMessage.setTimestamp(spin.getDate());
            chatMessage.setTimestamp(System.currentTimeMillis());
            return true;
        });
    }

    @NonNull
    public static Intent getFeedbackActivityIntent(Context context) {
        return new Intent(context, ForFeedbackActivity.class);
    }
}
