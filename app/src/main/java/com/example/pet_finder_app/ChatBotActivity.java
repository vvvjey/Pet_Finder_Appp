    package com.example.pet_finder_app;

    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;

    public class ChatBotActivity extends AppCompatActivity {

        // creating variables for our
        // widgets in xml file.
        private RecyclerView chatsRV;
        private ImageView sendMsgIB;
        private EditText userMsgEdt;
        private final String USER_KEY = "user";
        private final String BOT_KEY = "bot";

        // creating a variable for
        // our volley request queue.
        private RequestQueue mRequestQueue;

        // creating a variable for array list and adapter class.
        private ArrayList<MessageModal> messageModalArrayList;
        private MessageRVAdapter messageRVAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chatbot);

            // on below line we are initializing all our views.
            chatsRV = findViewById(R.id.idRVChats);
            sendMsgIB = findViewById(R.id.idIBSend);
            userMsgEdt = findViewById(R.id.idEdtMessage);

            // below line is to initialize our request queue.
            mRequestQueue = Volley.newRequestQueue(ChatBotActivity.this);
            mRequestQueue.getCache().clear();

            // creating a new array list
            messageModalArrayList = new ArrayList<>();

            // adding on click listener for send message button.
            sendMsgIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // checking if the message entered
                    // by user is empty or not.
                    if (userMsgEdt.getText().toString().isEmpty()) {
                        // if the edit text is empty display a toast message.
                        Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // calling a method to send message
                    // to our bot to get response.
                    sendMessage(userMsgEdt.getText().toString());

                    // below line we are setting text in our edit text as empty
                    userMsgEdt.setText("");
                }
            });

            // on below line we are initializing our adapter class and passing our array list to it.
            messageRVAdapter = new MessageRVAdapter(messageModalArrayList, this);

            // below line we are creating a variable for our linear layout manager.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBotActivity.this, RecyclerView.VERTICAL, false);

            // below line is to set layout
            // manager to our recycler view.
            chatsRV.setLayoutManager(linearLayoutManager);

            // below line we are setting
            // adapter to our recycler view.
            chatsRV.setAdapter(messageRVAdapter);
        }

        private void sendMessage(String userMsg) {
            // below line is to pass message to our
            // array list which is entered by the user.
            messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
            messageRVAdapter.notifyDataSetChanged();
            chatsRV.scrollToPosition(messageModalArrayList.size() - 1);

            // Base URL for the API
            String baseUrl = "http://api.brainshop.ai/get";
            String bid = "182276";
            String key = "QsKB0mtZcDWflt2C";
            String uid = "1"; // hoặc sử dụng một giá trị mặc định
            String msg = Uri.encode(userMsg);

            // Xây dựng lại URL
            String url = baseUrl + "?bid=" + bid + "&key=" + key + "&uid=" + uid + "&msg=" + msg;

            // creating a variable for our request queue.
            RequestQueue queue = Volley.newRequestQueue(ChatBotActivity.this);

            // on below line we are making a json object request for a get request and passing our url .
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String botResponse = response.getString("cnt");
                        messageModalArrayList.add(new MessageModal(botResponse, BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                        chatsRV.scrollToPosition(messageModalArrayList.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // handling error response from bot.
                        messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                        chatsRV.scrollToPosition(messageModalArrayList.size() - 1);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Error handling
                    messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                    chatsRV.scrollToPosition(messageModalArrayList.size() - 1);
                    Toast.makeText(ChatBotActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
                }
            });

            // at last adding json object
            // request to our queue.
            queue.add(jsonObjectRequest);
        }
    }
