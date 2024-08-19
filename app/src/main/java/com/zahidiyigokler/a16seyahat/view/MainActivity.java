package com.zahidiyigokler.a16seyahat.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.zahidiyigokler.a16seyahat.R;
import com.zahidiyigokler.a16seyahat.adapter.PlaceAdapter;
import com.zahidiyigokler.a16seyahat.databinding.ActivityMainBinding;
import com.zahidiyigokler.a16seyahat.model.Place;
import com.zahidiyigokler.a16seyahat.roomdb.PlaceDao;
import com.zahidiyigokler.a16seyahat.roomdb.PlaceDatabase;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    ArrayList<Place> places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        places = new ArrayList<>();
        PlaceDatabase db = Room.databaseBuilder(getApplicationContext(),
                PlaceDatabase.class, "Places").allowMainThreadQueries().build();
        PlaceDao placeDao = db.placeDao();
        mDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));
    }
    private void handleResponse(List<Place> placeList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceAdapter placeAdapter = new PlaceAdapter(Collections.singletonList(placeList));
        binding.recyclerView.setAdapter(placeAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.travel_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_place) {
            Intent intent = new Intent(this,MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }
}
