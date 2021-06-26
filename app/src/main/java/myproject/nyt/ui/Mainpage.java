package myproject.nyt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import myproject.nyt.R;

public class Mainpage extends AppCompatActivity implements View.OnClickListener
{
    LinearLayout laySearch,layView,layShare,layEmail;
    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        imgBack = findViewById(R.id.imgBack);           imgBack.setVisibility(View.GONE);
        laySearch = findViewById(R.id.laySearch);       laySearch.setOnClickListener(this);
        layView = findViewById(R.id.layView);           layView.setOnClickListener(this);
        layShare = findViewById(R.id.layShare);         layShare.setOnClickListener(this);
        layEmail = findViewById(R.id.layEmail);         layEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.laySearch:
            {
                startActivity(new Intent(Mainpage.this,SearchArticle.class));
                break;
            }
            case R.id.layView:
            {
                startActivity(new Intent(Mainpage.this,PopularArticle.class).putExtra("type","MV"));
                break;
            }
            case R.id.layShare:
            {
                startActivity(new Intent(Mainpage.this,PopularArticle.class).putExtra("type","MS"));
                break;
            }
            case R.id.layEmail:
            {
                startActivity(new Intent(Mainpage.this,PopularArticle.class).putExtra("type","ME"));
                break;
            }
        }
    }
}
