package assignment.fragment.rsantosh.com.threads;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDownload;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    Integer count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownload = (Button) findViewById(R.id.download_image);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mImageView = (ImageView) findViewById(R.id.image_view);

        mDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        startThread();

    }

    private void startThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new MyTask().execute(100);


                    }
                });

            }
        }).start();

    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {

            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(100);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {


            mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image));
   //         mProgressBar.setVisibility(View.GONE);
            //mStatus.setText(result);

        }
        @Override
        protected void onPreExecute() {
            //mStatus.setText("Task Starting...");
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(0);
            count=1;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
           // mStatus.setText("Running..."+ values[0]);
           // mPercentage.setText(values[0]+"0%");
            mProgressBar.setProgress(values[0]);
        }
    }

}