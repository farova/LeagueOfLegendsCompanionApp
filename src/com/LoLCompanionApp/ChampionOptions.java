package com.LoLCompanionApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ChampionOptions extends Activity {

	static final String[] OPTIONS = new String[] { "Guides", "Counterpicks",
			"Skills", "Skins" };
	static private String champion;
	DatabaseHelper database;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.champinfo);

		database = new DatabaseHelper(this);

		// get the name of the chosen champion
		champion = getIntent().getStringExtra("name");

		createHeader();

		// Creates Listview
		GridView gv = (GridView) findViewById(R.id.menuList);

		// Creates adapter
		gv.setAdapter(new ArrayAdapter<String>(this, R.layout.optionlist,
				OPTIONS));

		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String choice = (String) ((TextView) view).getText();

				// go to next page based on button pressed
				Intent Page2 = new Intent();
				Page2.setClassName("com.LoLCompanionApp", "com.LoLCompanionApp.Champion" + choice);
				Page2.putExtra("name", champion);
				startActivity(Page2);
			}
		});

		String[][] stats = database.getChampionStats(champion);
		// replace tab chars to spaces
		//stats = stats.replace("	", " ");

		// set stats text to textview
		TextView statsText = (TextView) findViewById(R.id.stats);
		statsText.setText("Attributes:\n"
				+ database.getChampionAttributes(champion)
				+ "\n\nBase Stats:\n" + "Range: " + stats[0][0]
						+"\n\nMove Speed: " + stats[1][0] 
						+"\n\nHealth: "+stats[7][0]+"("+stats[7][1]+")" 
						+"\n\nHealth Regen: "+stats[5][0]+"("+stats[5][1]+")"
						+"\n\nMana: "+stats[3][0]+"("+stats[3][1]+")"
						+"\n\nMana Regen: "+stats[4][0]+"("+stats[4][1]+")"
						+"\n\nAttack: "+stats[8][0]+"("+stats[8][1]+")"
						+"\n\nArmor: "+stats[2][0]+"("+stats[2][1]+")"
						+"\n\nMagic Resist: "+stats[6][0]+"("+stats[6][1]+")");

		// set lore text to text view
		TextView lore = (TextView) findViewById(R.id.loreText);
		lore.setText(database.getChampionLore(champion));

		database.close();
	}

	private void createHeader() {
		// Creates header
		TextView champName = (TextView) findViewById(R.id.champName);
		TextView champTitle = (TextView) findViewById(R.id.champTitle);
		ImageView champImage = (ImageView) findViewById(R.id.champPicture);
		champName.setText(champion);
		String champPic = database.changeSpecialChars(champion);
		int path = getResources().getIdentifier(champPic.toLowerCase(),
				"drawable", "com.LoLCompanionApp");

		champTitle.setText(database.getChampionTitle(champion));
		champImage.setImageResource(path);
	}

	public void back(View view) {
		finish();
	}

}
