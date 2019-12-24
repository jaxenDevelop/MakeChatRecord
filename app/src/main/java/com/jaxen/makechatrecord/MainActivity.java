package com.jaxen.makechatrecord;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaxen.makechatrecord.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    public static boolean REPEATDRAW = false;
    /**
     * 导航栏监听事件，与ViewPager滑动同步
     **/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**保持屏幕常亮**/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /**导航栏监听**/
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /**初始化FragmentList**/
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
//        list.add(new TurntableFragment());
//        list.add(new AboutFragment());

        /**viewpager初始化**/
        myFragAdapter myFragAdapter = new myFragAdapter(getSupportFragmentManager(), list);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(myFragAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**版本大于6.0，动态获取位置服务及文件读取权限**/
        if (Build.VERSION.SDK_INT >= 23) {
            String perms[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, perms)) {
                /**do nothing**/
            } else {
                EasyPermissions.requestPermissions(this, "应用需要从本地读取csv文件，请授予权限以正常使用",
                        777, perms);
            }

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case 777:
                Toast.makeText(this, "文件读取权限已获取！", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case 777:
                Toast.makeText(this, "未授权，请在系统设置中手动授予文件读取权限！", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
