package com.weijifen.demining;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button button;
    int boomNum = 10;
    Set set=new HashSet();
    int rows = 9;
    int columns = 9;
    List<Area> areaList = new ArrayList<>();
    GridView mainGridView;
    AreaAdapter areaAdapter;

    Handler viewHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            areaAdapter.setmDatas(areaList);
            areaAdapter.notifyDataSetChanged();
        }
    };
    Handler endHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, "恭喜您，您胜利了", Toast.LENGTH_LONG).show();
            button.setVisibility(View.VISIBLE);
            mainGridView.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0;i<rows;i++)
            for(int j=0;j<columns;j++)
                areaList.add(new Area());
//        areaList.get(34).setBomb(true);
        rand();
//        ram();
        init();
    }

    void rand() {
        Random random = new Random(System.currentTimeMillis());
        while (set.size() != boomNum)
        {
            set.add(random.nextInt(rows * columns));
        }
        for(Iterator it = set.iterator(); it.hasNext(); )
        {
//            System.out.println("value="+it.next().toString());
            areaList.get((int) it.next()).setBomb(true);
        }

    }

    public void ram() {
        int[] nums = new int[81];
        for(int i = 0;i < 100;i++){
            nums[i]=i;
        }
        randSelect(nums,10);
        for(int i = 0;i < 10; i ++){
            areaList.get(i).setBomb(true);
        }
    }

    private void init() {
        button = (Button) findViewById(R.id.button);
        mainGridView = (GridView)findViewById(R.id.main_girdView);
        mainGridView.setNumColumns(columns);
        areaAdapter = new AreaAdapter(MainActivity.this,areaList,R.layout.item_adapter);
        mainGridView.setAdapter(areaAdapter);
        mainGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!areaList.get(position).isSweep()) {
                    areaList.get(position).setFlag(!areaList.get(position).isFlag());
                    viewHandler.sendEmptyMessage(0);
                }
                return true;
            }
        });
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (areaList.get(position).isSweep()) return;
                if (areaList.get(position).isBomb()) {
                    Toast.makeText(MainActivity.this, "抱歉，您踩到了地雷", Toast.LENGTH_LONG).show();
                    down();
                    mainGridView.setEnabled(false);
                    button.setVisibility(View.VISIBLE);
                    viewHandler.sendEmptyMessage(0);
                }
                if (!areaList.get(position).isSweep()){
                    seep(position);
//                    areaList.get(position).setSweep(true);
                    viewHandler.sendEmptyMessage(0);
                    //是否完成
                    if (detection()){
                        endHandler.sendEmptyMessage(0);
                    }
                }
            }
        });
        /*mainGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                button.setVisibility(View.GONE);
//                mainGridView.setEnabled(true);
//                rand();
//                viewHandler.sendEmptyMessage(0);
                Intent intent;
                intent=new Intent(MainActivity.this,MainActivity.class);
//                intent.putExtra("ObjectId", s);
//                    intent.putExtra("tag_Type", tag_Type);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean detection() {
        for (Area a : areaList) {
            if(!a.isBomb() && !a.isSweep())
                return false;
        }
        return true;
    }

    private void down() {
        for (Area a : areaList) {
            if (a.isBomb())
                a.setSweep(true);
        }
    }

    void seep(int position) {
        Coordinate coo =one2two(position);
        dfs(coo.x,coo.y);
    }

    void dfs(int r, int c) {
        if(r<0 || r>=rows || c<0 || c>=columns){
            return;
        }
        if (areaList.get(two2one(r, c)).isSweep() || areaList.get(two2one(r, c)).isBomb())
            return;

        int cnt=0;
        for(int dr =-1;dr<=1;dr++) {
            for (int dc=-1;dc<=1;dc++) {
                if(dr!=0 ||dc!=0 )
                {
                    if(r+dr<0 || r+dr>=rows || c+dc<0 || c+dc>=columns){
                        continue;
                    }
                    if (areaList.get(two2one(r+dr, c+dc)).isBomb())
                        cnt++;
//                    else dfs(r+dr,c+dc);
                }
            }
        }
        areaList.get(two2one(r, c)).setNumber(cnt);
        areaList.get(two2one(r, c)).setSweep(true);
        if (areaList.get(two2one(r, c)).getNumber() == 0)
        {
            for(int dr =-1;dr<=1;dr++) {
                for (int dc=-1;dc<=1;dc++) {
                    if(dr!=0 ||dc!=0 )
                    {
//                        if(r+dr<0 || r+dr>=rows || c+dc<0 || c+dc>=columns){
//                            continue;
//                        }
//                        if (areaList.get(two2one(r+dr, c+dc)).isBomb())
//                            cnt++;
                    dfs(r+dr,c+dc);
                    }
                }
            }
        }
    }

    public static void randSelect(int[] nums, int n) {
        Random rand = new Random();
        for(int i = 0; i < n; i ++){
            swap(nums , i, rand.nextInt(nums.length-i)+i);
        }
    }

    public static void swap(int[] nums, int m , int n){
        int temp = nums[n];
        nums[n] = nums[m];
        nums[m] = temp;
    }
    Coordinate one2two(int position)
    {
        return new Coordinate(position / rows, position % columns);
    }
    int two2one(int r, int c)
    {
        return r * rows + c;
    }
}
