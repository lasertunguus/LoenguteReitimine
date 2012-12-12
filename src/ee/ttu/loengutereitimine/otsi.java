package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class otsi extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        TextView textview = new TextView(this);
        textview.setText("Siin me otsime");
        setContentView(textview);
    }
}
