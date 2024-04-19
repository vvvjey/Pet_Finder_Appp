package com.example.pet_finder_app;

public class FilterMissingSpinnerBreed extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        private Spinner spinner;
        private static final String[] paths = {"item 1", "item 2", "item 3"};

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.filtter_missing_pet);

            spinner = (Spinner)findViewById(R.id.spinnerBreed);
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
//                    android.R.layout.simple_spinner_item,paths);

//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

            switch (position) {
                case 0:
                    // Whatever you want to happen when the first item gets selected
                    break;
                case 1:
                    // Whatever you want to happen when the second item gets selected
                    break;
                case 2:
                    // Whatever you want to happen when the thrid item gets selected
                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }

}

