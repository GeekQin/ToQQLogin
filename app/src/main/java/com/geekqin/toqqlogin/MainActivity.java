package com.geekqin.toqqlogin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private TextView qqNum;
    private TextView copy_qq;
   private ImageView QQ_Group;
   //此处为自己所建群的key，获取地址为：https://qun.qq.com/join.html
   private String key = "B-b-a4DKmzLvz7KDQCNVF-rn1S04VvBu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qqNum = (TextView) findViewById(R.id.qqNum);
        QQ_Group = (ImageView) findViewById(R.id.iv_go_qq_group);
        copy_qq = (TextView)findViewById(R.id.copy_qq);
        Log.i("login","进入到初始化函数");
//        toChatting();//启动QQ客户端到聊天界面
        goQQGroup();
    }

    //跳转到加群界面
    private void goQQGroup() {
        QQ_Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinQQGroup(key);
            }
        });
    }

    private void toChatting() {
        QQ_Group.setOnClickListener(new View.OnClickListener() {

//      跳转到QQ加好友界面uri："mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum+ "&version=1"
            @Override
            public void onClick(View v) {
                if (checkApkExit(MainActivity.this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum+ "&version=1")));
                } else {
                    Toast.makeText(MainActivity.this, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //通过包名检索应用是否存在
    public boolean checkApkExit(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)){
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    getPackageManager().GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /****************
     *
     * 发起添加群流程。群号：IT互联网交流(229678638) 的 key 为： B-b-a4DKmzLvz7KDQCNVF-rn1S04VvBu
     * 调用 joinQQGroup(B-b-a4DKmzLvz7KDQCNVF-rn1S04VvBu) 即可发起手Q客户端申请加群 IT互联网交流(229678638)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(MainActivity.this,"未安装手Q或安装的版本不支持",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
