// Spencer Jones
// MDV3832-0 - 062024
// MainActivity.java

package com.example.jonesspencer_ce04;

// Imports
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity"; // Logging tag

    // UI Elements
    private Spinner viewSpinner;
    private Spinner adapterSpinner;
    private ListView listView;
    private GridView gridView;
    private ArrayAdapter<String> arrayAdapter;
    private SimpleAdapter simpleAdapter;
    private CustomBaseAdapter customBaseAdapter;
    private List<Person> personList;
    private List<Map<String, String>> personData;

    // Person class
    public class Person { // Changed from private to public
        private String firstName;
        private String lastName;
        private String birthday;
        private int picture;

        // Constructor
        public Person(String firstName, String lastName, String birthday, int picture) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.picture = picture;
        }

        // Getters
        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getBirthday() {
            return birthday;
        }

        public int getPicture() {
            return picture;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        viewSpinner = findViewById(R.id.view_spinner);
        adapterSpinner = findViewById(R.id.adapter_spinner);
        listView = new ListView(this);
        gridView = new GridView(this);
        gridView.setNumColumns(2);

        // Setup person list and adapters
        setupPersonList();
        setupAdapters();

        // Set default view to ListView and ArrayAdapter
        setView(listView);
        listView.setAdapter(arrayAdapter);

        // Set listeners for spinner selection changes
        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setView(listView);
                } else {
                    setView(gridView);
                }
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set item click listener for ListView and GridView
        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            Person person = personList.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(person.getFirstName() + " " + person.getLastName())
                    .setMessage(person.getBirthday())
                    .setIcon(person.getPicture())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        };

        listView.setOnItemClickListener(itemClickListener);
        gridView.setOnItemClickListener(itemClickListener);
    }

    // Method to setup person list
    private void setupPersonList() {
        personList = new ArrayList<>();
        personList.add(new Person("Elon", "Musk", "06/28/1971", R.drawable.elonmusk));
        personList.add(new Person("Jeff", "Bezos", "01/12/1964", R.drawable.jeffbezos));
        personList.add(new Person("Mark", "Zuckerberg", "05/14/1984", R.drawable.markzuckerberg));
        personList.add(new Person("Sundar", "Pichai", "06/10/1972", R.drawable.sundarpichai));
        personList.add(new Person("Jack", "Dorsey", "11/19/1976", R.drawable.jackdorsey));
        personList.add(new Person("Susan", "Wojcicki", "07/06/1968", R.drawable.susanwojcicki));
        personList.add(new Person("Jan", "Koum", "07/06/1968", R.drawable.jankoum));
        personList.add(new Person("Andrej", "Karpathy", "10/23/1986", R.drawable.andrejkarpathy));
        personList.add(new Person("Tobi", "Lütke", "07/16/1981", R.drawable.tobilutke));
        personList.add(new Person("Tim", "Cook", "11/01/1960", R.drawable.timcook));

        // List of maps to hold person data for SimpleAdapter
        personData = new ArrayList<>();
        for (Person person : personList) {
            Map<String, String> data = new HashMap<>();
            data.put("name", person.getFirstName() + " " + person.getLastName());
            data.put("birthday", person.getBirthday());
            personData.add(data);
        }
    }

    // Method to setup adapters for ListView and GridView
    private void setupAdapters() {
        // ArrayAdapter for displaying person names
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getPersonNames());

        // SimpleAdapter for displaying person names and birthdays
        simpleAdapter = new SimpleAdapter(this, personData, R.layout.simple_adapter,
                new String[]{"name", "birthday"},
                new int[]{R.id.name, R.id.birthday});

        // CustomBaseAdapter for displaying person details with images
        customBaseAdapter = new CustomBaseAdapter(this, personList);
    }

    // Method to get list of person names
    private List<String> getPersonNames() {
        List<String> names = new ArrayList<>();
        for (Person person : personList) {
            names.add(person.getFirstName() + " " + person.getLastName());
        }
        return names;
    }

    // Method to set view in container
    private void setView(View view) {
        FrameLayout container = findViewById(R.id.container);
        container.removeAllViews();
        container.addView(view);
    }

    // Method to update adapter based on selected spinner positions
    private void updateAdapter() {
        int viewPosition = viewSpinner.getSelectedItemPosition();
        int adapterPosition = adapterSpinner.getSelectedItemPosition();

        // Set appropriate adapter based on selected view and adapter type
        if (viewPosition == 0) { // ListView
            if (adapterPosition == 0) {
                listView.setAdapter(arrayAdapter);
            } else if (adapterPosition == 1) {
                listView.setAdapter(simpleAdapter);
            } else {
                listView.setAdapter(customBaseAdapter);
            }
        } else { // GridView
            if (adapterPosition == 0) {
                gridView.setAdapter(arrayAdapter);
            } else if (adapterPosition == 1) {
                gridView.setAdapter(simpleAdapter);
            } else {
                gridView.setAdapter(customBaseAdapter);
            }
        }
    }
}