package algonquin.cst2335.ouni0002;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.ouni0002.databinding.ReceiveMessageBinding;
import algonquin.cst2335.ouni0002.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;
    Button sendButton;
    TextView textInput;
    TextView sentMessage;
    private ChatMessageDao mDAO;
    Button receiveButton;
    ChatMessage chatMessage = null;
    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        recyclerView = findViewById(R.id.recycleView);
        sendButton = findViewById(R.id.sendButton);
        textInput = findViewById(R.id.textInput);
        receiveButton = findViewById(R.id.recieveButton);

        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(gridLayoutManager);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.setValue( messages = new ArrayList<ChatMessage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
                mDAO = db.cmDAO();
                List<ChatMessage> allMessages = mDAO.getAllMessages();
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
//                binding.recycleView.setAdapter( myAdapter ); //You can then load the RecyclerView
            });
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                chatMessage = new ChatMessage(textInput.getText().toString(), currentDateandTime, false);
                chatMessage.setTimeSent(currentDateandTime);
                chatMessage.setMessage(textInput.getText().toString());
                chatMessage.setSentButton(true);
                messages.add(chatMessage);
                myAdapter.notifyItemInserted(messages.size()-1);
                // messageText=chatMessage.getMessage();
                textInput.setText("");


            }
        });
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                chatMessage = new ChatMessage(textInput.getText().toString(), currentDateandTime, false);
                chatMessage.setTimeSent(currentDateandTime);
                chatMessage.setMessage(textInput.getText().toString());
                chatMessage.setSentButton(false);
                messages.add(chatMessage);
                myAdapter.notifyItemInserted(messages.size()-1);
                // messageText=chatMessage.getMessage();
                textInput.setText("");


            }
        });

        recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
//                return new MyRowHolder(binding.getRoot());
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding2 = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding2.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

                String obj = chatMessage.message;
                holder.messageText.setText(obj);
                String obj2 = chatMessage.timeSent;
                holder.timeText.setText(obj2);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position) {
                if (messages.get(position).isSentButton()==true){
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        });


    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(clk ->{
                int position = getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete this message: "+messageText.getText());
                builder.setTitle("Question:");
                builder.setPositiveButton("Yes",(dialog, cl)->{
                    ChatMessage m=messages.get(position);
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->
                    {
                        mDAO.deleteMessage(m);
                    });

                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(messageText, "You deleted message #"+position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", undo->{

                                thread.execute(() ->
                                {
                                    mDAO.insertMessage(m);
                                });
                                messages.add(position,m);
                                myAdapter.notifyItemInserted(position);

                            }).show();
                });
                builder.setNegativeButton("No",(dialog, cl)->{});
                builder.create().show();
            });

            messageText = itemView.findViewById (R.id.messageText) ;
            timeText= itemView.findViewById(R.id.timeText);

        }
    }
}
