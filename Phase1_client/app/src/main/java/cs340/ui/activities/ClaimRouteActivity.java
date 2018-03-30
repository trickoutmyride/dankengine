package cs340.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs340.ui.R;

public class ClaimRouteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Button.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public static final String CARDS = "cards";
    public static final String COLOR = "color";
    public static final String SIZE = "size";
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String TAG = ClaimRouteActivity.class.getSimpleName();
    private List<String> availableColors;

    public List<String> getAvailableColors() {
        List<String> colors = new ArrayList<>();
        Map<String, Integer> quantities = new HashMap<>();
        Integer needed = getSize();
        Log.d(TAG, Arrays.toString(getCards()));
        if (getColor().equals("gray") || getColor().equals("grey")) {
            for (String card : getCards()) {
                Log.d(TAG, "Card is " + card);
                if (quantities.containsKey(card)) {
                    quantities.put(card, quantities.get(card) + 1);
                } else {
                    quantities.put(card, 1);
                }
            }
            Log.d(TAG, "Quantities:");
            for (Map.Entry<String, Integer> quantity : quantities.entrySet()) {
                Log.d(TAG, quantity.getKey() + ": " + quantity.getValue().toString());
            }
            if (quantities.containsKey("wild")) {
                needed -= quantities.get("wild");
                quantities.remove("wild");
            }
            for (Map.Entry<String, Integer> quantity : quantities.entrySet()) {
                if (quantity.getValue() >= needed) {
                    colors.add(quantity.getKey());
                }
            }
            if (colors.isEmpty()) {
                colors.add("--none--");
            }
        } else {
            colors.add(getColor());
        }
        return colors;
    }

    public String[] getCards() {
        return getCards(getIntent());
    }

    public static String[] getCards(Intent intent) {
        return intent.getStringArrayExtra(CARDS);
    }

    public String getColor() {
        return getColor(getIntent());
    }

    public static String getColor(Intent intent) {
        return intent.getStringExtra(COLOR);
    }

    public Integer getSize() {
        return getSize(getIntent());
    }

    public static Integer getSize(Intent intent) {
        return intent.getIntExtra(SIZE, 0);
    }

    public String getStart() {
        return getStart(getIntent());
    }

    public static String getStart(Intent intent) {
        return intent.getStringExtra(START);
    }

    public String getStop() {
        return getStop(getIntent());
    }

    public static String getStop(Intent intent) {
        return intent.getStringExtra(STOP);
    }

    @Override
    public void onClick(View view) {
        Log.d("cancel id", Integer.toString(R.id.cancel));
        Log.d("submit id", Integer.toString(R.id.submit));
        Log.d("view id", Integer.toString(view.getId()));
        if (view.getId() == R.id.cancel) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (view.getId() == R.id.submit) {
            sendResult();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_route);

        TextView routeView = findViewById(R.id.route);
        Spinner spinner = findViewById(R.id.color_select);
        SeekBar colorView = findViewById(R.id.colored_cards);
        SeekBar wildView = findViewById(R.id.wild_cards);

        String route = getStart() + " -> " + getStop();
        routeView.setText(route);

        availableColors = getAvailableColors();
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableColors);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        colorView.setOnSeekBarChangeListener(this);
        wildView.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        SeekBar colorView = findViewById(R.id.colored_cards);
        SeekBar wildView = findViewById(R.id.wild_cards);
        Button cancelButton = findViewById(R.id.cancel);
        Button submitButton = findViewById(R.id.submit);
        String color = availableColors.get(position);
        Integer cardCount = 0;
        Integer wildCount = 0;

        for (String card : getCards()) {
            if (card.equals(color)) {
                cardCount++;
            } else if (card.equals("wild")) {
                wildCount++;
            }
        }
        colorView.setMax(cardCount);
        wildView.setMax(wildCount);

        Integer colorDefault = Math.min(getSize(), cardCount);
        Integer wildDefault = Math.min(getSize() - colorDefault, wildCount);
        colorView.setProgress(colorDefault);
        wildView.setProgress(wildDefault);

        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SeekBar colorView = findViewById(R.id.colored_cards);
        TextView remainingView = findViewById(R.id.spaces_remaining);
        Button submitButton = findViewById(R.id.submit);
        SeekBar wildView = findViewById(R.id.wild_cards);
        Integer remaining = getSize() - colorView.getProgress() - wildView.getProgress();
        String spacesRemaining = "Spaces Remaining: " + remaining.toString();
        remainingView.setText(spacesRemaining);
        submitButton.setEnabled(remaining == 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void sendResult() {
        SeekBar colorView = findViewById(R.id.colored_cards);
        SeekBar wildView = findViewById(R.id.wild_cards);
        Spinner spinner = findViewById(R.id.color_select);
        String cardColor = spinner.getSelectedItem().toString();
        Integer colorCount = colorView.getProgress();
        Integer wildCount = wildView.getProgress();
        ArrayList<String> cards = new ArrayList<>();

        for (Integer i = 0; i < colorCount; i++) cards.add(cardColor);
        for (Integer i = 0; i < wildCount; i++) cards.add("wild");

        Intent intent = new IntentFactory()
                .cards(cards.toArray(new String[0]))
                .color(getColor())
                .context(this)
                .start(getStart())
                .stop(getStop())
                .createIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public static class IntentFactory {
        private String[] cards;
        private String color;
        private Context context;
        private Integer size;
        private String start;
        private String stop;

        public IntentFactory() {}

        public String[] cards() {
            return cards;
        }

        public IntentFactory cards(String[] cards) {
            this.cards = cards;
            return this;
        }

        public String color() {
            return color;
        }

        public IntentFactory color(String color) {
            this.color = color;
            return this;
        }

        public Context context() {
            return context;
        }

        public IntentFactory context(Context context) {
            this.context = context;
            return this;
        }

        public Intent createIntent() {
            Intent intent = new Intent(context, ClaimRouteActivity.class);
            intent.putExtra(CARDS, cards);
            intent.putExtra(COLOR, color);
            intent.putExtra(SIZE, size);
            intent.putExtra(START, start);
            intent.putExtra(STOP, stop);
            return intent;
        }

        public Integer size() {
            return size;
        }

        public IntentFactory size(Integer size) {
            this.size = size;
            return this;
        }

        public String start() {
            return start;
        }

        public IntentFactory start(String start) {
            this.start = start;
            return this;
        }

        public String stop() {
            return stop;
        }

        public IntentFactory stop(String stop) {
            this.stop = stop;
            return this;
        }
    }
}
